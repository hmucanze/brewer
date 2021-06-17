package com.mucanze.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.mucanze.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this(getDefault().getPath(System.getProperty("user.home"), ".brewerfotos"));
		//this(getDefault().getPath(System.getenv("HOME"), ".brewerfoto"));
	}
	
	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		if(files != null && files.length > 0) {
			MultipartFile file = files[0];
			novoNome = renomearArquivo(file.getOriginalFilename());
			try {
				file.transferTo(new File(localTemporario.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando a foto na pasta temporária", e);
			}
		}
		return novoNome;
	}
	
	@Override
	public byte[] recuperarFotoTemporaria(String foto) {
		try {
			return Files.readAllBytes(localTemporario.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("erro lendo a foto da cerveja", e);
		}
	}
	
	@Override
	public byte[] recuperar(String foto) {
		try {
			return Files.readAllBytes(local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto da cerveja", e);
		}
	}
	
	@Override
	public void salvar(String foto) {
		try {
			Files.move(localTemporario.resolve(foto), local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro movendo a foto para o local definitivo", e);
		}
		
		try {
			Thumbnails.of(local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando o thumbnail da foto", e);
		}
	}
	
	private String renomearArquivo(String originalFilename) {
		String novoNome = UUID.randomUUID() + "_" + originalFilename;
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Nome original %s, novo nome %s", originalFilename, novoNome));
		}
		
		return novoNome;
	}

	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pastas criadas com sucesso!");
				LOGGER.debug("Pasta default " + this.local.toAbsolutePath());
				LOGGER.debug("Pasta temporaria " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar a foto", e);
		}
	}

}
