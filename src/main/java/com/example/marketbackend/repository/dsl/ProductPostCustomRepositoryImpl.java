package com.example.marketbackend.repository.dsl;

import com.example.marketbackend.entity.ProductPost;
import com.example.marketbackend.entity.QProductPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductPostCustomRepositoryImpl implements ProductPostCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final QProductPost qProductPost = QProductPost.productPost;

    @Override
    public Page<ProductPost> findByKeyword(String address, String title, String content, String category, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // Address 조건 추가
        if (!address.isEmpty()) {
            builder.and(qProductPost.address.eq(address));
        }

        // Title, Content, Category 부분 일치 조건 추가
        builder.and(
            qProductPost.title.contains(title)
                .or(qProductPost.content.contains(content))
                .or(qProductPost.category.contains(category))
        );

        // 정렬 추가
        queryFactory.selectFrom(qProductPost)
                .where(builder)
                .orderBy(qProductPost.id.desc()) // 필요에 따라 정렬 추가
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        // Page 객체 생성
        return new PageImpl<>(queryFactory.selectFrom(qProductPost)
                .where(builder)
                .orderBy(qProductPost.id.desc()) // 필요에 따라 정렬 추가
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults()
                .getResults(), pageable, count(builder));
    }

    private long count(BooleanBuilder builder) {
        return queryFactory.selectFrom(qProductPost)
                .where(builder)
                .fetchCount();
    }
}
