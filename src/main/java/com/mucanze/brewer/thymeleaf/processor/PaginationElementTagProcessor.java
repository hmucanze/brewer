package com.mucanze.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class PaginationElementTagProcessor extends AbstractElementTagProcessor {
	
	private static final String NOME_ELEMENTO = "pagination";
	private static final int PRECEDENCE = 1000; 

	public PaginationElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, NOME_ELEMENTO, true, null, false, PRECEDENCE);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		
		IAttribute page = tag.getAttribute("page");
		
		IModelFactory modelFactory = context.getModelFactory();
		IModel model = modelFactory.createModel();
		model.add(modelFactory.createStandaloneElementTag("th:block",
				"th:replace",
				String.format("fragments/Paginacao.html :: paginacao (%s)", page.getValue())));
		
		structureHandler.replaceWith(model, true);
	}

}
