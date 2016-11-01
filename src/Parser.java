import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alex on 28/10/2016.
 */

public class Parser {

    private static final String QUESTION_SELECTOR = "table tr td[style] table tr td table[cellspacing=\"3\"]";
    private static final String TEXT_SELECTOR = ".cell_1 > form > table > tbody > tr:nth-child(1) p";
    private static final String ANSWER_SELECTOR = ".cell_1 > form > table > tbody > tr:nth-child(2) tr";
    private static final String ID_SELECTOR = "td[align=\"right\"] > span[style=\"color:#D0D0D0\"]";
    private static final String LINKS_SELECTOR = ".walk";


    private Document doc;

    public Parser(String page) {
        doc = Jsoup.parse(page);
    }

    public int getCountOfLinkedPages() {
        Element linkElement = doc.select(LINKS_SELECTOR).last();
        String link = linkElement.text();
        link = link.substring(1, link.length() - 1);

        return Integer.parseInt(link);
    }

    public Question[] getQuestions() {
        Elements questionElements = doc.select(QUESTION_SELECTOR);

        List<Question> questions = questionElements.parallelStream()
                .map(this::parseQuiz)
                .collect(Collectors.toList());

        return questions.toArray(new Question[]{});
    }

    private Question parseQuiz(Element element) {
        Question question = new Question();

        question.id = element.select(ID_SELECTOR).text();
        question.text = element.select(TEXT_SELECTOR).text();

        element.select(ANSWER_SELECTOR).forEach(
                e -> question.answers.add(e.text().substring(8).trim())
        );

        return question;
    }

}
