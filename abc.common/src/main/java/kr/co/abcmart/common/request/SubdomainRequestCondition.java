package kr.co.abcmart.common.request;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubdomainRequestCondition implements RequestCondition<SubdomainRequestCondition> {

    private final String REPLACE_SUB_DOMAIN_REGEX = "\\.[^.]*\\.[^.]{2,3}(?:\\.[^.]{2,3})?$";

    private final Set<String> subdomains;
    private final String tld;

    public SubdomainRequestCondition(String tld, String... subdomains) {
        this(tld, Arrays.asList(subdomains));
    }

    public SubdomainRequestCondition(String tld, Collection<String> subdomains) {
        this.subdomains = Collections.unmodifiableSet(new HashSet<String>(subdomains));
        this.tld = tld;
    }

    @Override
    public SubdomainRequestCondition combine(SubdomainRequestCondition other) {
        Set<String> allRoles = new LinkedHashSet<String>(this.subdomains);
        allRoles.addAll(other.subdomains);
        return new SubdomainRequestCondition(tld, allRoles);
    }

    @Override
    public SubdomainRequestCondition getMatchingCondition(HttpServletRequest request) {

        try {
            URL uri = new URL(request.getRequestURL().toString());
            String host = uri.getHost();
            String parts = "".equals(this.tld) ? 
                           host.replaceAll(REPLACE_SUB_DOMAIN_REGEX, "") : 
                        host.replace(".".concat(this.tld), "");
            for (String s : this.subdomains) {

               if (s.equalsIgnoreCase(parts)) {
                    return this;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int compareTo(SubdomainRequestCondition other,HttpServletRequest request) {
        return CollectionUtils.removeAll(other.subdomains, this.subdomains).size();
    }
    
    public static void main(String[] args) {
      String domain = "localmo.abcmart.co.kr";
      System.out.println(domain.replaceAll("\\.[^.]*\\.[^.]{2,3}(?:\\.[^.]{2,3})?$", ""));
      
   }

}