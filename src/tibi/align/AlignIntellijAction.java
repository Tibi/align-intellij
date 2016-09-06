package tibi.align;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;


public class AlignIntellijAction extends EditorAction {
	public AlignIntellijAction() {
		super(new EditorActionHandler() {
			@Override
			public void execute(Editor editor, DataContext dataContext) {
				Application application = ApplicationManager.getApplication();
				AlignComponent compo = application.getComponent(AlignComponent.class);
				compo.alignText(editor);
			}
		});
	}
}
