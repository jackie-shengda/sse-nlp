package nlp.recognition.impl;

import nlp.domain.Nature;
import nlp.domain.Result;
import nlp.domain.Term;
import nlp.recognition.Recognition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 基于规则的新词发现 jijiang feidiao
 * 
 * @author ansj
 * 
 */
public class BookRecognition implements Recognition {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Nature nature = new Nature("book");

    private static Map<String, String> ruleMap = new HashMap<String, String>();

    static {
        ruleMap.put("《", "》");
    }

    @Override
    public void recognition(Result result) {
        List<Term> terms = result.getTerms();
        String end = null;
        String name;

        LinkedList<Term> mergeList = null;

        List<Term> list = new LinkedList<Term>();

        for (Term term : terms) {
            name = term.getName();
            if (end == null) {
                if ((end = ruleMap.get(name)) != null) {
                    mergeList = new LinkedList<Term>();
                    mergeList.add(term);
                } else {
                    list.add(term);
                }
            } else {
                mergeList.add(term);
                if (end.equals(name)) {

                    Term ft = mergeList.pollFirst();
                    for (Term sub : mergeList) {
                        ft.merage(sub);
                    }
                    ft.setNature(nature);
                    list.add(ft);
                    mergeList = null;
                    end = null;
                }
            }
        }

        if (mergeList != null) {
            for (Term term : list) {
                list.add(term);
            }
        }

        result.setTerms(list);
    }

}
