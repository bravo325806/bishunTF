package nutc.imac.edu.bishuntf;

import android.content.Intent;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by 5*N on 2017/6/5.
 */

public class SplitArray {
    public String getJsonString(HashMap<String, String> params) {
        JSONObject jsonObject = new JSONObject(params);
        String json = jsonObject.toString();
        String[] splitJson = json.split("\\[|]");
        String result = "";
        for (int i = 0; i < splitJson.length; i++) {
            String split = splitJson[i];
            if (i == 0 && splitJson.length > 1) {
                split = split.substring(0, split.length() - 1) + "[\"";
            } else if (i == 1) {
                split = getMacIdString(split);
            } else if (i == 2) {
                split = "\"]" + split.substring(1, split.length());
            }
            result = result + split;
        }

        return result;
    }
    public String getJsonInteger(HashMap<String, Integer> params) {
        JSONObject jsonObject = new JSONObject(params);
        String json = jsonObject.toString();
        String[] splitJson = json.split("\\[|]");
        String result = "";
        for (int i = 0; i < splitJson.length; i++) {
            String split = splitJson[i];
            if (i == 0 && splitJson.length > 1) {
                split = split.substring(0, split.length() - 1) + "[\"";
            } else if (i == 1) {
                split = getMacIdString(split);
            } else if (i == 2) {
                split = "\"]" + split.substring(1, split.length());
            }
            result = result + split;
        }

        return result;
    }
    private String getMacIdString(String split) {
        String[] dotsplit = split.split(", ");
        String result = "";
        for (int i = 0; i < dotsplit.length; i++) {
            if (i < (dotsplit.length - 1)) {
                result = result + dotsplit[i] + "\",\"";
            } else {
                result = result + dotsplit[i];
            }
        }
        return result;
    }
}
