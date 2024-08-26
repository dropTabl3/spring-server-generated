package io.swagger.api;

import io.swagger.model.Student;

import java.net.URI;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-08-21T08:27:53.170218228Z[GMT]")
@RestController
public class StudentsApiController implements StudentsApi {

    private static final Logger log = LoggerFactory.getLogger(StudentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private List<Student> db = new ArrayList<>();

    @org.springframework.beans.factory.annotation.Autowired
    public StudentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> addStudent(@Parameter(in = ParameterIn.DEFAULT, description = "Update an existent student", required=true, schema=@Schema()) @Valid @RequestBody Student body) {
        body.setId(UUID.randomUUID());
        db.add(body);
        URI uri = URI.create("v1/students/" + body.getId());
        return ResponseEntity.created(uri).build();
    }

    public ResponseEntity<?> deleteStudentById(@Parameter(in = ParameterIn.PATH, description = "id", required=true, schema=@Schema()) @PathVariable("id") UUID id) {
        boolean removed = db.removeIf(s -> s.getId().equals(id));
        if(removed){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> listStudents() {
        return db.isEmpty() ? ResponseEntity.noContent().build() : new ResponseEntity<List>(db, HttpStatus.OK);
    }

    public ResponseEntity<?> replaceStudent(@Parameter(in = ParameterIn.DEFAULT, description = "replace an existent student", required=true, schema=@Schema()) @Valid @RequestBody Student body) {
        if(db.contains(body)) {
            db.set(db.indexOf(body), body);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
