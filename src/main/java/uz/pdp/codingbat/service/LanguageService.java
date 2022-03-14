package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Language;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.LanguageDto;
import uz.pdp.codingbat.repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    public List<Language> all(){
        return languageRepository.findAll();
    }

    public Language getById(Integer id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        return optionalLanguage.orElse(null);
    }

    public ApiResponse addLanguage(LanguageDto languageDto){
        boolean exists = languageRepository.existsByName(languageDto.getName());
        if (exists)
            return new ApiResponse("Name already exist!",false);
        Language language=new Language();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return new ApiResponse("Successfully added!",true);
    }

    public ApiResponse editLanguage(Integer id,LanguageDto languageDto){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent())
            return new ApiResponse("Language not found!",false);
        boolean exists = languageRepository.existsByName(languageDto.getName());
        if (exists)
            return new ApiResponse("Name already exist!",false);
        Language language=optionalLanguage.get();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return new ApiResponse("Successfully edited!",true);
    }

    public ApiResponse deleteLanguage(Integer id){
        try {
            languageRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!",true);
        }catch (Exception e){
            return new ApiResponse("Deleting error!",false);
        }
    }
}
