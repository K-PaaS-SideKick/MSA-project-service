//package kpaas.cumulonimbus.kpaas_project_service.Entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//public class Image {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @Lob
//    private byte[] data;
//
//    @ManyToOne
//    @JoinColumn(name = "pid")
//    private Project project;
//}
