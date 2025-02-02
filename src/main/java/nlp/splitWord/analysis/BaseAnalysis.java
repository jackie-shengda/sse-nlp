package nlp.splitWord.analysis;

import nlp.domain.Result;
import nlp.util.AnsjReader;
import nlp.domain.Term;
import nlp.splitWord.Analysis;
import nlp.util.Graph;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * 基本的分词.只做了.ngram模型.和数字发现.其他一律不管
 * 
 * @author ansj
 * 
 */
public class BaseAnalysis extends Analysis {

    @Override
    protected List<Term> getResult(final Graph graph) {
        Merger merger = new Merger() {
            @Override
            public List<Term> merger() {
                graph.walkPath();
                return getResult();
            }

            private List<Term> getResult() {
                List<Term> result = new ArrayList<Term>();
                int length = graph.terms.length - 1;
                for (int i = 0; i < length; i++) {
                    if (graph.terms[i] != null) {
                        result.add(graph.terms[i]);
                    }
                }

                setRealName(graph, result);
                return result;
            }
        };
        return merger.merger();
    }

    public BaseAnalysis() {};

    public BaseAnalysis(Reader reader) {
        super.resetContent(new AnsjReader(reader));
    }

    public static Result parse(String str) {
        return new BaseAnalysis().parseStr(str);
    }
}
