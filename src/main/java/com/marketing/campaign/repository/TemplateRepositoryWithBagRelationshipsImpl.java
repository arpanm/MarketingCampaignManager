package com.marketing.campaign.repository;

import com.marketing.campaign.domain.Template;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class TemplateRepositoryWithBagRelationshipsImpl implements TemplateRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Template> fetchBagRelationships(Optional<Template> template) {
        return template.map(this::fetchTemplateParams);
    }

    @Override
    public Page<Template> fetchBagRelationships(Page<Template> templates) {
        return new PageImpl<>(fetchBagRelationships(templates.getContent()), templates.getPageable(), templates.getTotalElements());
    }

    @Override
    public List<Template> fetchBagRelationships(List<Template> templates) {
        return Optional.of(templates).map(this::fetchTemplateParams).orElse(Collections.emptyList());
    }

    Template fetchTemplateParams(Template result) {
        return entityManager
            .createQuery(
                "select template from Template template left join fetch template.templateParams where template.id = :id",
                Template.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Template> fetchTemplateParams(List<Template> templates) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, templates.size()).forEach(index -> order.put(templates.get(index).getId(), index));
        List<Template> result = entityManager
            .createQuery(
                "select template from Template template left join fetch template.templateParams where template in :templates",
                Template.class
            )
            .setParameter("templates", templates)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
