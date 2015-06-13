package org.protege.editor.owl.ui.view.relation;

import java.awt.BorderLayout;
import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

public class OWLRelationViewComponent extends AbstractOWLViewComponent {
    private static final long serialVersionUID = -4515710047558710080L;
    private static final Logger log = Logger.getLogger(OWLRelationViewComponent.class);
    private Metrics metricsComponent;

    @Override
    protected void initialiseOWLView() throws Exception {
        setLayout(new BorderLayout());
        metricsComponent = new Metrics(getOWLModelManager());
        add(metricsComponent, BorderLayout.CENTER);
        log.info("Example View Component initialized");
    }

	@Override
	protected void disposeOWLView() {
		metricsComponent.dispose();
	}
}
