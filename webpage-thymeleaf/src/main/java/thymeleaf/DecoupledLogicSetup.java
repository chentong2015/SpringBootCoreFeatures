package thymeleaf;

import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Component
public class DecoupledLogicSetup {

    private final SpringResourceTemplateResolver templateResolver;

    // 属性或可通过application.properties来设置
    // setUseDecoupledLogic(): Enable the decoupled template logic
    public DecoupledLogicSetup(SpringResourceTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
        this.templateResolver.setUseDecoupledLogic(true);
    }
}
