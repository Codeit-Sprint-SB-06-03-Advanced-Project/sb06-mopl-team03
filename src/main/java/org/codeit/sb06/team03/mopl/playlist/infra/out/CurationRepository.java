package org.codeit.sb06.team03.mopl.playlist.infra.out;

import com.querydsl.core.group.GroupBy;
import io.github.openfeign.querydsl.jpa.spring.repository.QuerydslJpaRepository;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.Curation;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.CurationId;
import org.codeit.sb06.team03.mopl.playlist.domain.entity.QCuration;

import java.util.*;

public interface CurationRepository extends QuerydslJpaRepository<Curation, CurationId> {

    boolean existsById(CurationId id);

    List<Curation> id(CurationId id);

    Optional<Curation> findById(CurationId id);

    void deleteById(CurationId id);

    default List<UUID> findAllByPlaylistId(UUID playlistId) {
        QCuration curation = QCuration.curation;
        return select(curation.id.contentId)
                .where(curation.id.playlistId.eq(playlistId))
                .from(curation)
                .fetch();
    }

    default void deleteAllByPlaylistId(UUID playlistId) {
        QCuration curation = QCuration.curation;
        delete(curation)
                .where(curation.id.playlistId.eq(playlistId))
                .execute();
    }

    default void deleteAllByContentId(UUID playlistId) {
        QCuration curation = QCuration.curation;
        delete(curation)
                .where(curation.id.contentId.eq(playlistId))
                .execute();
    }

    default Map<UUID, List<UUID>> findAllByPlaylistIdsIn(List<UUID> playlistIds) {
        if (playlistIds == null || playlistIds.isEmpty()) {
            return Collections.emptyMap();
        }

        QCuration curation = QCuration.curation;
        return select(curation.id.contentId)
                .from(curation)
                .where(curation.id.playlistId.in(playlistIds))
                .transform(
                        GroupBy.groupBy(curation.id.playlistId)
                                .as(GroupBy.list(curation.id.contentId))
                );
    }

}
