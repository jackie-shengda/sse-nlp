package nlp.recognition;

import nlp.domain.Term;

/**
 * 词语识别接口,用来识别词语
 * 
 * @author Ansj
 *
 */
public interface TermArrRecognition {
    public void recognition(Term[] terms);
}
