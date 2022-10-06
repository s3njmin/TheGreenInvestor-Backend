package G2T6.G2T6.G2T6.options;

import java.util.List;
import org.springframework.stereotype.Service;

import G2T6.G2T6.G2T6.questions.QuestionRepository;


@Service
public class OptionServiceImpl implements OptionService {
   
    private OptionRepository options;
    

    public OptionServiceImpl(OptionRepository options){
        this.options = options;
    }

    @Override
    public List<Option> listOptions() {
        return options.findAll();
    }

    
    @Override
    public Option getOption(Long id){
        return options.findById(id).orElse(null);
    }
    
 
    @Override
    public Option addOption(Option option) {
        List<Option> sameTitles = options.findByOption(option.getOption());
        if(sameTitles.size() == 0)
            return options.save(option);
        else
            return null;
    }
    
    @Override
    public Option updateOption(Long id, Option newOptionInfo){
        return options.findById(id).map(option -> {option.setOption(newOptionInfo.getOption());
            return options.save(option);
        }).orElse(null);

    }

    @Override
    public void deleteOption(Long id){
        options.deleteById(id);
    }

}
