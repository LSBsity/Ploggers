package coderookie.plogging.repository;

import coderookie.plogging.common.SearchEnum;
import coderookie.plogging.domain.QImage;
import coderookie.plogging.dto.object.PostMainResponse;
import coderookie.plogging.search.SearchCond;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static coderookie.plogging.domain.QImage.*;
import static coderookie.plogging.domain.QPost.*;
import static coderookie.plogging.domain.QUser.*;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PostMainResponse> findByCond(SearchCond searchCond, Pageable pageable) {

        JPQLQuery<Long> getTitleImage = JPAExpressions
                .select(image1.id.min())
                .from(image1)
                .where(image1.post.id.eq(post.id));

        return queryFactory
                .select(Projections.constructor(
                        PostMainResponse.class,
                        post.id,
                        post.title,
                        JPAExpressions.select(image1.image)
                                .from(image1)
                                .where(image1.id.eq(getTitleImage)),
                        post.category,
                        post.viewCount,
                        post.likesCount,
                        post.commentCount,
                        post.createdTime,
                        user.nickname,
                        user.profileImage
                ))
                .from(post)
                .where(eqKeyword(searchCond))
                .join(post.user, user)
                .orderBy(post.createdTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression eqKeyword(SearchCond searchEnum) {

        if (searchEnum == null) {
            return null;
        }

        if (searchEnum.getSearchEnum() == null && searchEnum.getWord() != null) {
            return post.title.contains(searchEnum.getWord()).or(post.content.contains(searchEnum.getWord()));
        }

        if (searchEnum.getSearchEnum().equals(SearchEnum.NICKNAME)) {
            return post.user.nickname.eq(searchEnum.getWord());
        }

        if (searchEnum.getSearchEnum().equals(SearchEnum.TITLE)) {
            return post.title.contains(searchEnum.getWord());
        }

        if (searchEnum.getSearchEnum().equals(SearchEnum.CONTENT)) {
            return post.content.contains(searchEnum.getWord());
        }

        return null;
    }


}
