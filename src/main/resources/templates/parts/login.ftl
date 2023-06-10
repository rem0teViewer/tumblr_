<#include "security.ftl">

<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User Name : </label>
            <div class="col-sm-6">
                <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="User name"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError};
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password : </label>
            <div class="col-sm-6">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError};
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Password : </label>
                <div class="col-sm-6">
                    <input type="password" name="password2"
                           class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Confirm password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error};
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email : </label>
                <div class="col-sm-6">
                    <input type="email" name="email"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                           placeholder="@mail"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError};
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="g-recaptcha" data-sitekey="6LdOIwMhAAAAACKknhIqRCvjmiVCgZI41NZ6aDw0"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <br>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a class="btn btn-dark" href="/registration">Register</a></#if>
        <button class="btn btn-success ml-3" type="submit"><#if isRegisterForm>Create<#else >Sign In</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if user??>
            <button class="btn btn-info" type="submit">Log Out</button>
        <#else>
            <button class="btn btn-success" type="submit">Log In</button></#if>
    </form>
</#macro>