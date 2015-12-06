/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.yas99en.moreemacsnb.core.actions;

import io.github.yas99en.moreemacsnb.core.utils.CodePointIterator;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.EditorActionRegistration;

/**
 *
 * @author Yasuhiro Endoh
 */
@EditorActionRegistration(name="io-github-yas99en-moreemacsnb-core-actions-BackwardWordAction")
public class BackwardWordAction extends MoreEmacsAction {
    public BackwardWordAction() {
        super("backward-word");
    }

    @Override
    public void actionPerformed(ActionEvent e, JTextComponent target) {
        Caret caret = target.getCaret();
        int prev = getPreviousWordPosition(target.getDocument(), caret.getDot());
        caret.setDot(prev);
    }
    
    public static int getPreviousWordPosition(Document doc, int offset) {
        CharSequence seq;
        try {
            seq = doc.getText(0, offset);
        } catch (BadLocationException ex) {
            Logger.getLogger(BackwardWordAction.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage(), ex);
        }
        CodePointIterator itr = new CodePointIterator(seq, seq.length());
        
        for(; itr.hasPrevious(); ) {
            if (Character.isLetterOrDigit(itr.previous())) {
                itr.next();
                break;
            }
        }
        for(; itr.hasPrevious(); ) {
            if (!Character.isLetterOrDigit(itr.previous())) {
                itr.next();
                break;
            }
        }
        return itr.index();
    }
}