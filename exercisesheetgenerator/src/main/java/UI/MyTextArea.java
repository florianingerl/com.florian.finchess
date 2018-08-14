package UI;

import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;

public class MyTextArea extends TextArea {

	public MyTextArea() {
		setEditable(false);
		focusedProperty().addListener((observalbe, oldFocusedState, newFocusedState) -> {
			  setFocused(false);
			});
	}
	
	static Text helper = new Text();
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        
        ScrollBar scrollBarv = (ScrollBar) this.lookup(".scroll-bar:vertical");
        if (scrollBarv != null) {
            System.out.println("hiding vbar");
            ((ScrollPane) scrollBarv.getParent()).setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
        ScrollBar scrollBarh = (ScrollBar) this.lookup(".scroll-bar:horizontal");
        if (scrollBarh != null) {
            System.out.println("hiding hbar");
            ((ScrollPane) scrollBarh.getParent()).setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
    }

    @Override
    protected double computePrefWidth(double width) {
        Bounds bounds = getTextBounds();
        Insets insets = getInsets();
        double w = Math.ceil(bounds.getWidth() + insets.getLeft() + insets.getRight());
        return w;
    }

    @Override
    protected double computePrefHeight(double height) {
        Bounds bounds = getTextBounds();
        Insets insets = getInsets();
        double h = Math.ceil(bounds.getHeight() + insets.getLeft() + insets.getRight());
        return h;
    }

    //from https://stackoverflow.com/questions/15593287/binding-textarea-height-to-its-content/19717901#19717901
    public Bounds getTextBounds() {
        //String text = (textArea.getText().equals("")) ? textArea.getPromptText() : textArea.getText();
        String text = "";
        text = this.getParagraphs().stream().map((p) -> p + "W\n").reduce(text, String::concat);
        text += "W";
        helper.setText(text);
        helper.setFont(this.getFont());
        // Note that the wrapping width needs to be set to zero before
        // getting the text's real preferred width.
        helper.setWrappingWidth(0);
        return helper.getLayoutBounds();
    }
}


