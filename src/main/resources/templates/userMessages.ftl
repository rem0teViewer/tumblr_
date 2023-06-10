<#import "parts/common.ftl" as c>

<@c.page>
    <h3>${userBlog.username}</h3>
    <#if !isCurrentUser>
        <#if isSubscriber>
            <a class="btn btn-secondary" href="/user/unsubscribe/${userBlog.id}">Unsubscribe</a>
        <#else>
            <a class="btn btn-danger" href="/user/subscribe/${userBlog.id}">Subscribe</a>
        </#if>
    </#if>
    <div class="container my-3">
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Subscriptions</div>
                        <h3 class="card-text">
                            <a href="/user/subscriptions/${userBlog.id}/list">${subscriptionsCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Subscribers</div>
                        <h3 class="card-text">
                            <a href="/user/subscribers/${userBlog.id}/list">${subscribersCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#if isCurrentUser>
        <#if message??>
            <#include "parts/messageEdit.ftl"/>
        </#if>
    </#if>

    <#include "parts/messageList.ftl"/>

</@c.page>