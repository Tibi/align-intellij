package tibi.align;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

/**
 * @author YCHT
 */
public class AlignComponent implements ApplicationComponent {
	public void initComponent() {
	}

	public void disposeComponent() {
	}

	@NotNull public String getComponentName() {
		return "Align";
	}

	void alignText(Editor editor) {
		String separator = Messages.showInputDialog(editor.getContentComponent(), "Enter separator:",
				"Alignment", null, ",", null);
		SelectionModel selectionModel = editor.getSelectionModel();
		if (selectionModel.getSelectedText() == null) return;
		Document document = editor.getDocument();
		final int startOffset = document.getLineStartOffset(document.getLineNumber(selectionModel.getSelectionStart()));
		final int endOffset = document.getLineEndOffset(document.getLineNumber(selectionModel.getSelectionEnd()));
		final String text = document.getText(new TextRange(startOffset, endOffset));

		String newText = Align.align(text, separator);

		ApplicationManager.getApplication().runWriteAction(() ->
				editor.getDocument().replaceString(startOffset, endOffset, newText));
	}
}
