<#include "security.ftl">
<#import "pager.ftl" as p>

<@p.pager page url />

<div class="card-columns" id="message-list">
    <#list  page.content as message>
        <div class="card my-3" data-id="${message.id}">
            <#if message.author.id == currentUserId>
                <div class="float-sm-left">
                    <#if message.author.id == currentUserId>
                        <a class="btn btn-light"
                           title="Edit"
                           href="/user-messages/${message.author.id}?message=${message.id}">&#9998;
                        </a>
                    </#if>
                </div>
                <div class="float-sm-right">
                    <form method="post" action="/user-messages/${message.author.id}?message=${message.id}">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input type="hidden" name="id" value="${message.id}"/>
                        <input type="submit" class="btn btn-light" title="Delete" value="&#9587;">
                    </form>
                </div>
            </#if>
            <#if message.filename??>
                <img src="/img/${message.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${message.text}</span></br>
                <strong><i>#${message.tag}</i></strong>
            </div>
            <div class="card-footer text-muted container">
                <div class="row">
                    <a class="col align-self-right"
                       href="/user-messages/${message.author.id}"><strong>${message.authorName}</strong>
                    </a>
                    <a class="col align-self-center" href="/messages/${message.id}/like">
                        <#if message.meLiked>
                            <i class="fas fa-heart"></i>
                        <#else>
                            <i class="far fa-heart"></i>
                        </#if>
                        ${message.likes}
                    </a>
                    <div class="float-sm-right">
                        <i class="m-3">${message.getCreatedDate()}</i>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <strong>Blog is empty...</strong>
    </#list>
</div>

<@p.pager page url />