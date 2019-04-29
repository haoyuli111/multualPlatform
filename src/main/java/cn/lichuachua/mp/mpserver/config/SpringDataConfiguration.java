package cn.lichuachua.mp.mpserver.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class SpringDataConfiguration {

//    @Bean
//    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
//        return new AlternateTypeRuleConvention() {
//            @Override
//            public int getOrder() {
//                return Ordered.HIGHEST_PRECEDENCE;
//            }
//
//            @Override
//            public List<AlternateTypeRule> rules() {
//                return newArrayList(newRule(resolver.resolve(Pageable.class), resolver.resolve(Page.class)));
//            }
//        };
//    }
//
//    @ApiModel
//    static class Page {
//        /**
//         * 加此三个注解会报异常java.lang.NumberFormatException:For input string:""
//         */
////        @ApiModelProperty("Results page you want to retrieve (0..N)")
//        private Integer page;
//
////        @ApiModelProperty("Number of records per page")
//        private Integer size;
//
////        @ApiModelProperty("Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.")
//        private List<String> sort;
//
//        public Integer getPage() {
//            return page;
//        }
//
//        public void setPage(Integer page) {
//            this.page = page;
//        }
//
//        public Integer getSize() {
//            return size;
//        }
//
//        public void setSize(Integer size) {
//            this.size = size;
//        }
//
//        public List<String> getSort() {
//            return sort;
//        }
//
//        public void setSort(List<String> sort) {
//            this.sort = sort;
//        }
//    }
}
