package com.grupo24.cerraduras_casas.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class GoogleCalendarService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public void createCalendarEvent(String accessToken, String summary, Date startDate, Date endDate)
            throws GeneralSecurityException, IOException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(request -> {
            request.getHeaders().setAuthorization("Bearer " + accessToken);
            request.getHeaders().setContentType("application/json");
        });

        // Formatear fechas en formato RFC3339
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String startDateTime = dateFormat.format(startDate);
        String endDateTime = dateFormat.format(endDate);

        // Crear el contenido del evento
        Map<String, Object> event = new HashMap<>();
        event.put("summary", summary);
        event.put("description", "Acceso generado automáticamente por SmartAccess");

        Map<String, String> start = new HashMap<>();
        start.put("dateTime", startDateTime);
        start.put("timeZone", "Europe/Madrid");
        event.put("start", start);

        Map<String, String> end = new HashMap<>();
        end.put("dateTime", endDateTime);
        end.put("timeZone", "Europe/Madrid");
        event.put("end", end);

        // Crear la solicitud HTTP
        JsonHttpContent content = new JsonHttpContent(JSON_FACTORY, event);
        GenericUrl url = new GenericUrl("https://www.googleapis.com/calendar/v3/calendars/primary/events");

        HttpRequest request = requestFactory.buildPostRequest(url, content);
        HttpResponse response = request.execute();

        System.out.println("✅ Evento creado en Google Calendar: " + response.parseAsString());
        System.out.println(response.parseAsString());
    }
}