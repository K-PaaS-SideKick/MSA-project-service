//package kpaas.cumulonimbus.kpaas_project_service.Service;
//
//import jakarta.persistence.EntityNotFoundException;
//import kpaas.cumulonimbus.kpaas_project_service.DAO.ImageRepository;
//import kpaas.cumulonimbus.kpaas_project_service.Entity.Image;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@Service
//public class ImageService {
//    private final ImageRepository imageRepository;
//
//    public ImageService(ImageRepository imageRepository) {
//        this.imageRepository = imageRepository;
//    }
//
//    public Image saveImage(MultipartFile file) throws IOException {
//        Image image = Image.builder()
//                .data(file.getBytes())
//                .name(file.getName())
//                .build();
//        return imageRepository.save(image);
//    }
//
//    public byte[] getImageById(Long id) {
//        Optional<Image> imageOptional = imageRepository.findById(id);
//        if(imageOptional.isPresent()) {
//            return imageOptional.get().getData();
//        }
//        else{
//            throw new EntityNotFoundException("image not found");
//        }
//    }
//}
