package si.fri.rso.samples.hikecatalog.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.samples.hikecatalog.lib.HikeMetadata;
import si.fri.rso.samples.hikecatalog.models.converters.HikeConverter;
import si.fri.rso.samples.hikecatalog.models.entities.HikeEntity;


@RequestScoped
public class HikeBean {

    private Logger log = Logger.getLogger(HikeBean.class.getName());

    @Inject
    private EntityManager em;

    public List<HikeMetadata> getHikeMetadata() {

        TypedQuery<HikeEntity> query = em.createNamedQuery(
                "HikeEntity.getAll", HikeEntity.class);

        List<HikeEntity> resultList = query.getResultList();

        return resultList.stream().map(HikeConverter::toDto).collect(Collectors.toList());

    }


    public HikeMetadata getHikeMetaData(Integer id) {

        HikeEntity hikeEntity = em.find(HikeEntity.class, id);

        if (hikeEntity == null) {
            throw new NotFoundException();
        }

        HikeMetadata hike = HikeConverter.toDto(hikeEntity);

        return hike;
    }

    public HikeMetadata createHikeMetadata(HikeMetadata hikeMetadata) {

        HikeEntity hikeEntity = HikeConverter.toEntity(hikeMetadata);

        try {
            beginTx();
            em.persist(hikeEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (hikeEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return HikeConverter.toDto(hikeEntity);
    }

    public HikeMetadata putHikeMetadata(Integer id, HikeMetadata hikeMetadata) {

        HikeEntity c = em.find(HikeEntity.class, id);

        if (c == null) {
            return null;
        }

        HikeEntity updatedHikeEntity = HikeConverter.toEntity(hikeMetadata);

        try {
            beginTx();
            updatedHikeEntity.setId(c.getId());
            updatedHikeEntity = em.merge(updatedHikeEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return HikeConverter.toDto(updatedHikeEntity);
    }

    public boolean deleteHikeMetadata(Integer id) {

        HikeEntity hike = em.find(HikeEntity.class, id);

        if (hike != null) {
            try {
                beginTx();
                em.remove(hike);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
