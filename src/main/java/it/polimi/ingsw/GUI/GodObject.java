package it.polimi.ingsw.GUI;

import javafx.scene.image.Image;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.polimi.ingsw.Model.God;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Objects;

public class GodObject {

    private String godNameLabel;
    private Image podiumGodImage;
    private Image powerIconImage;
    private String actionTypeLabel;
    private String actionDescriptionLabel;

    private JSONObject jsonObject;

    public GodObject(God selected){
        JSONParser parser = new JSONParser();

        try {
            URL jsonURL = getClass().getClassLoader().getResource("gods.json");
            assert jsonURL != null;
            File file = new File(jsonURL.getFile());
            String path = URLDecoder.decode(file.toString(), "UTF-8");

            jsonObject = (JSONObject) parser.parse(new FileReader(path));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        JSONObject actionType = (JSONObject) jsonObject.get(selected.toString());
        JSONObject actionDescription = (JSONObject) jsonObject.get(selected.toString());

        godNameLabel = selected.toString();
        actionTypeLabel = actionType.get("type").toString();
        actionDescriptionLabel = actionDescription.get("description").toString();

        switch (selected) {
            case APOLLO:
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Apolo.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0044_god_and_hero_powers0014.png")).toExternalForm());
                break;
            case ARTEMIS:
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Artemis.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0048_god_and_hero_powers0010.png")).toExternalForm());
                break;
            case ATHENA:
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Athena.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0050_god_and_hero_powers0008.png")).toExternalForm());
                break;
            case ATLAS:
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Atlas.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0049_god_and_hero_powers0009.png")).toExternalForm());
                break;
            case DEMETER:
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Demeter.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0052_god_and_hero_powers0006.png")).toExternalForm());
                break;
            case HEPHAESTUS:
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Hephaestus.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0052_god_and_hero_powers0006.png")).toExternalForm());
                break;
            case MINOTAUR:
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Minotaur.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0009_god_and_hero_powers0049.png")).toExternalForm());
                break;
            case PAN:
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Pan.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0056_god_and_hero_powers0002.png")).toExternalForm());
                break;
            case PROMETHEUS:
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Prometheus.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0005_god_and_hero_powers0053.png")).toExternalForm());
                break;
            default:
                podiumGodImage = null;
                powerIconImage = null;
        }
    }

    public String getGodNameLabel() {
        return godNameLabel;
    }

    public Image getPodiumGodImage() {
        return podiumGodImage;
    }

    public Image getPowerIconImage() {
        return powerIconImage;
    }

    public String getActionTypeLabel() {
        return actionTypeLabel;
    }

    public String getActionDescriptionLabel() {
        return actionDescriptionLabel;
    }
}
