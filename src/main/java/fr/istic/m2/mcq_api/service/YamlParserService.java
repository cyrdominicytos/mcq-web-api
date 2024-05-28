package fr.istic.m2.mcq_api.service;

import fr.istic.m2.mcq_api.dto.QcmYamlDTO;
import fr.istic.m2.mcq_api.dto.QuestionYamlDTO;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.List;
/**
 * @author Cyriaque TOSSOU, Tuo Adama
 * Service to manage QCM creation with Yaml content
 */
@Service
public class YamlParserService {

    public String getDefautQcmYamlString() {
        return  """
              questions:
                - title: "Quelle est la capitale de la France ?"
                  active: true
                  delay: 30
                  complexity: 1
                  answers:
                    - title: "Paris"
                      active: true
                      valid: true
                    - title: "Londres"
                      active: true
                      valid: false
                    - title: "Berlin"
                      active: true
                      valid: false
                    - title: "Madrid"
                      active: true
                      valid: false
                - title: "Quelle est la plus grande planète du système solaire ?"
                  active: true
                  delay: 45
                  complexity: 2
                  answers:
                    - title: "Terre"
                      active: true
                      valid: false
                    - title: "Mars"
                      active: true
                      valid: false
                    - title: "Jupiter"
                      active: true
                      valid: true
                    - title: "Saturne"
                      active: true
                      valid: false
                - title: "Quelle est la formule chimique de l'eau ?"
                  active: true
                  delay: 15
                  complexity: 1
                  answers:
                    - title: "H2O"
                      active: true
                      valid: true
                    - title: "CO2"
                      active: true
                      valid: false
                    - title: "O2"
                      active: true
                      valid: false
                    - title: "H2SO4"
                      active: true
                      valid: false
                """;
    }

    /**
     * parse a giving yaml text in to List of question to create QCM
     * @param yamlContent
     * @return
     * @throws Exception
     */
    public static List<QuestionYamlDTO> parseYaml(String yamlContent) throws  Exception{
        try
        {
            LoaderOptions options = new LoaderOptions();
            Constructor constructor = new Constructor(QcmYamlDTO.class, options);
            Yaml yaml = new Yaml(constructor);
            QcmYamlDTO dto = yaml.load(yamlContent);
            return dto.getQuestions();
        }catch (Exception e){
            throw  new Exception(e.getMessage());

        }
    }

    /*public static String convertQuestionsToYaml(List<Question> questions) {
        Qcm qcm = new Qcm();
        qcm.setQuestions(questions);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        Representer representer = new Representer();
        representer.addClassTag(QcmYamlDTO.class, Tag.MAP);
        representer.addClassTag(QuestionYamlDTO.class, Tag.MAP);
        representer.addClassTag(AnswerYamlDTO.class, Tag.MAP);

        Yaml yaml = new Yaml(representer, options);
        return yaml.dump(qcm);
    }*/

}
