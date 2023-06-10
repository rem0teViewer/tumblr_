<#import "parts/common.ftl" as c>
<@c.page>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Users</th>
            <th scope="col">Roles</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td><a class="btn btn-warning" href="/user/${user.id}">${user.username}</a></td>
                <td><#list user.roles as role>${role}<#sep> , </#list></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>