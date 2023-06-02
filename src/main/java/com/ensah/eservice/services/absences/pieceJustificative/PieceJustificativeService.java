package com.ensah.eservice.services.absences.pieceJustificative;

import com.ensah.eservice.models.File;
import com.ensah.eservice.models.PieceJustificative;
import com.ensah.eservice.repositories.FileRepository;
import com.ensah.eservice.repositories.PieceJustificativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PieceJustificativeService {

   private final PieceJustificativeRepository pieceJustificativeRepository;
   private final FileRepository fileRepository;


   public PieceJustificative addPieceJustificative(MultipartFile piece) throws IOException {
      File file = new File();
      file.setType(piece.getContentType());
      file.setData(piece.getBytes());

      PieceJustificative pieceJustificative = new PieceJustificative();
      pieceJustificative.setSource(fileRepository.save(file));
      pieceJustificative.setIntitule(piece.getName());
      pieceJustificative.setDateLivraison(new Date());
      return pieceJustificativeRepository.save(pieceJustificative);
   }


}
