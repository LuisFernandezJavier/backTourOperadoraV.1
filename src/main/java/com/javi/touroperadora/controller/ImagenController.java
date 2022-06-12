package com.javi.touroperadora.controller;

import com.javi.touroperadora.entity.Imagen;
import com.javi.touroperadora.repository.ImagenRepository;
import com.javi.touroperadora.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/imagen")
@CrossOrigin(origins = "http://localhost:4200")
public class ImagenController {

    @Autowired
    ImagenRepository imagenRepository;

    @Autowired
    ImagenService imagenService;

    @PostMapping("/subida")
    public BodyBuilder subirImagen (@RequestParam("imagenfile")MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        Imagen imagen = new Imagen(file.getOriginalFilename(), file.getContentType(), compressZLib(file.getBytes()));
        imagenRepository.save(imagen);
        return  ResponseEntity.status(HttpStatus.OK);
    }


    @GetMapping("/lista")
    public ResponseEntity<List<Imagen>> list() {
        List<Imagen> list = imagenService.list();
        return new ResponseEntity<>(list , HttpStatus.OK);
    }

    @GetMapping("/getImagen/{imagenId}")
    public Imagen getImagen(@PathVariable("imagenId") Long imagenId) {

        final Optional<Imagen> retrievedImage = imagenRepository.findById(imagenId);
        Imagen imagen = new Imagen(retrievedImage.get().getNombreImagen(),retrievedImage.get().getTipoImagen(),decompressZLib(retrievedImage.get().getImagenByte()));
        return imagen;
    }

    //metodo con el que comprimo los bytes de la imagen antes de almacenarla en la base de datos
    public static byte[] compressZLib(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }
    public static byte[] decompressZLib(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    }
