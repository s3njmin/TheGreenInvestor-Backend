package G2T6.G2T6.G2T6.options;

import java.util.List;

public interface OptionService {
    List<Option> listOptions();
    Option getOption(Long id);
    Option addOption(Option option);
    Option updateOption(Long id, Option option);
    void deleteOption(Long id);
}
