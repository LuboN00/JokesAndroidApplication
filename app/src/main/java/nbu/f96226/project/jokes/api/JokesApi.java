package nbu.f96226.project.jokes.api;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import nbu.f96226.project.jokes.Utils.Constants;
import nbu.f96226.project.jokes.model.Flags;
import nbu.f96226.project.jokes.model.JokesApiResponse;

public class JokesApi {

    public static class fetchJoke extends AsyncTask<Void, Void, JokesApiResponse> {

        @Override
        protected JokesApiResponse doInBackground(Void ... params) {
            HttpURLConnection urlConnection = null;


            try {
                URL url = new URL(Constants.JOKES_BASE_URL);
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();

                if (code !=  200) {
                    throw new IOException("Invalid response from server: " + code);
                }

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream())
                );

                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = rd.readLine()) != null) {
                    sb.append(line + "\n");
                }

                return buildResponse(sb.toString());

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        private JokesApiResponse buildResponse(String payloadJSON) throws JSONException {
            JSONObject response = new JSONObject(payloadJSON);
            JSONObject flags = response.getJSONObject("flags");
            return new JokesApiResponse(
                    response.getBoolean("error"),
                    response.getString("category"),
                    response.getString("type"),
                    response.getString("joke"),
                    response.getInt("id"),
                    response.getBoolean("safe"),
                    response.getString("lang"),
                    new Flags(
                            flags.getBoolean("nsfw"),
                            flags.getBoolean("religious"),
                            flags.getBoolean("political"),
                            flags.getBoolean("racist"),
                            flags.getBoolean("sexist"),
                            flags.getBoolean("explicit")
                    )
            );
        }
    }
}
