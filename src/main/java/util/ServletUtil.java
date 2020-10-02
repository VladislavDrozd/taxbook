package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import def.DBPool;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

public class ServletUtil {
    private static final Logger log = Logger.getLogger(ServletUtil.class);

    public ServletUtil(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    private HttpServletRequest request;
    private HttpServletResponse response;

    public <OUT>void sendDTO(int statusCode, OUT dto) {
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PrintWriter writer = response.getWriter();
            response.setStatus(statusCode);
            objectMapper.writeValue(writer, dto);
        } catch (Exception e) {
            log.error("Error in sendDTO()", e);
        }
    }

    public void sendError(String errorStr) {
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            response.setStatus(500);
            writer.write("Error on server: " + errorStr + " \nContact the administrator.");
        } catch (Exception e) {
            log.error("Error in sendDTO()", e);
        }
    }

    public <OUT>void sendDTOCollection(int statusCode, Collection<OUT> dtoCollection) {
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        try {
            PrintWriter writer = response.getWriter();
            response.setStatus(statusCode);
            mapper.writeValue(writer, dtoCollection);
        } catch (Exception e) {
            log.error("Error in sendDTOCollection()", e);
        }
    }
    public <IN>IN deserializeDTO(Class<IN> dtoClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = parseJSONString();
            /*if (json == null || json.isEmpty()) {
                json = (String) this.request.getAttribute("jsonStr");
            }*/
            return (json != null) ? mapper.readValue(json, dtoClass) : null;
        } catch (JsonProcessingException e) {
            log.error("Error in deserializeDTO()", e);
            return null;
        }
    }
    private String parseJSONString() {
        try (BufferedReader reader = this.request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("Exception in parseJSONString: " + e.getMessage(), e);
            return null;
        }
    }

    public <IN> List<IN> deserializeDTOList(Class<IN> dtoClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = parseJSONString();
            if (json == null || json.isEmpty()) {
                json = (String) this.request.getAttribute("jsonStr");
            }
            return mapper.readValue(json,  new TypeReference<List<IN>>() { });
        } catch (JsonProcessingException e){
            log.error("Exception in deserializeDTOList: " + e.getMessage(), e);
            return null;
        }
    }

}
