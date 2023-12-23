package com.sametb.cinequiltapp.auditing;

import com.sametb.cinequiltapp.user.User;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

    /**
     * Implementation of the Spring Data JPA {@link AuditorAware} interface for providing
     * information about the current auditor (user) during entity auditing.
     */

    public class ApplicationAuditAware implements AuditorAware<Integer> {

    /**
     * Retrieves the ID of the current auditor (user) for auditing purposes.
     *
     * @return An {@link Optional} containing the ID of the current auditor,
     *         or an empty {@link Optional} if no authenticated user is found.
     */
    @Override
    @NonNull //!!
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
            !authentication.isAuthenticated() ||
                // added for further usage our app not supports anonymous auth yet
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        User userPrincipal = (User) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
}
