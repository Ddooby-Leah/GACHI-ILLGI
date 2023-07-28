package com.ddooby.gachiillgi.domain.service.impl;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.services.simpleemail.waiters.AmazonSimpleEmailServiceWaiters;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class FakeMailService implements AmazonSimpleEmailService {
    private String subject;

    @Override
    public SendEmailResult sendEmail(SendEmailRequest sendEmailRequest) {

        this.subject = sendEmailRequest.getMessage().getSubject().getData();

        return new SendEmailResult().withMessageId("1");
    }

    @Override
    public void setEndpoint(String endpoint) {

    }

    @Override
    public void setRegion(Region region) {

    }

    @Override
    public CloneReceiptRuleSetResult cloneReceiptRuleSet(CloneReceiptRuleSetRequest cloneReceiptRuleSetRequest) {
        return null;
    }

    @Override
    public CreateConfigurationSetResult createConfigurationSet(CreateConfigurationSetRequest createConfigurationSetRequest) {
        return null;
    }

    @Override
    public CreateConfigurationSetEventDestinationResult createConfigurationSetEventDestination(CreateConfigurationSetEventDestinationRequest createConfigurationSetEventDestinationRequest) {
        return null;
    }

    @Override
    public CreateConfigurationSetTrackingOptionsResult createConfigurationSetTrackingOptions(CreateConfigurationSetTrackingOptionsRequest createConfigurationSetTrackingOptionsRequest) {
        return null;
    }

    @Override
    public CreateCustomVerificationEmailTemplateResult createCustomVerificationEmailTemplate(CreateCustomVerificationEmailTemplateRequest createCustomVerificationEmailTemplateRequest) {
        return null;
    }

    @Override
    public CreateReceiptFilterResult createReceiptFilter(CreateReceiptFilterRequest createReceiptFilterRequest) {
        return null;
    }

    @Override
    public CreateReceiptRuleResult createReceiptRule(CreateReceiptRuleRequest createReceiptRuleRequest) {
        return null;
    }

    @Override
    public CreateReceiptRuleSetResult createReceiptRuleSet(CreateReceiptRuleSetRequest createReceiptRuleSetRequest) {
        return null;
    }

    @Override
    public CreateTemplateResult createTemplate(CreateTemplateRequest createTemplateRequest) {
        return null;
    }

    @Override
    public DeleteConfigurationSetResult deleteConfigurationSet(DeleteConfigurationSetRequest deleteConfigurationSetRequest) {
        return null;
    }

    @Override
    public DeleteConfigurationSetEventDestinationResult deleteConfigurationSetEventDestination(DeleteConfigurationSetEventDestinationRequest deleteConfigurationSetEventDestinationRequest) {
        return null;
    }

    @Override
    public DeleteConfigurationSetTrackingOptionsResult deleteConfigurationSetTrackingOptions(DeleteConfigurationSetTrackingOptionsRequest deleteConfigurationSetTrackingOptionsRequest) {
        return null;
    }

    @Override
    public DeleteCustomVerificationEmailTemplateResult deleteCustomVerificationEmailTemplate(DeleteCustomVerificationEmailTemplateRequest deleteCustomVerificationEmailTemplateRequest) {
        return null;
    }

    @Override
    public DeleteIdentityResult deleteIdentity(DeleteIdentityRequest deleteIdentityRequest) {
        return null;
    }

    @Override
    public DeleteIdentityPolicyResult deleteIdentityPolicy(DeleteIdentityPolicyRequest deleteIdentityPolicyRequest) {
        return null;
    }

    @Override
    public DeleteReceiptFilterResult deleteReceiptFilter(DeleteReceiptFilterRequest deleteReceiptFilterRequest) {
        return null;
    }

    @Override
    public DeleteReceiptRuleResult deleteReceiptRule(DeleteReceiptRuleRequest deleteReceiptRuleRequest) {
        return null;
    }

    @Override
    public DeleteReceiptRuleSetResult deleteReceiptRuleSet(DeleteReceiptRuleSetRequest deleteReceiptRuleSetRequest) {
        return null;
    }

    @Override
    public DeleteTemplateResult deleteTemplate(DeleteTemplateRequest deleteTemplateRequest) {
        return null;
    }

    @Override
    public DeleteVerifiedEmailAddressResult deleteVerifiedEmailAddress(DeleteVerifiedEmailAddressRequest deleteVerifiedEmailAddressRequest) {
        return null;
    }

    @Override
    public DescribeActiveReceiptRuleSetResult describeActiveReceiptRuleSet(DescribeActiveReceiptRuleSetRequest describeActiveReceiptRuleSetRequest) {
        return null;
    }

    @Override
    public DescribeConfigurationSetResult describeConfigurationSet(DescribeConfigurationSetRequest describeConfigurationSetRequest) {
        return null;
    }

    @Override
    public DescribeReceiptRuleResult describeReceiptRule(DescribeReceiptRuleRequest describeReceiptRuleRequest) {
        return null;
    }

    @Override
    public DescribeReceiptRuleSetResult describeReceiptRuleSet(DescribeReceiptRuleSetRequest describeReceiptRuleSetRequest) {
        return null;
    }

    @Override
    public GetAccountSendingEnabledResult getAccountSendingEnabled(GetAccountSendingEnabledRequest getAccountSendingEnabledRequest) {
        return null;
    }

    @Override
    public GetCustomVerificationEmailTemplateResult getCustomVerificationEmailTemplate(GetCustomVerificationEmailTemplateRequest getCustomVerificationEmailTemplateRequest) {
        return null;
    }

    @Override
    public GetIdentityDkimAttributesResult getIdentityDkimAttributes(GetIdentityDkimAttributesRequest getIdentityDkimAttributesRequest) {
        return null;
    }

    @Override
    public GetIdentityMailFromDomainAttributesResult getIdentityMailFromDomainAttributes(GetIdentityMailFromDomainAttributesRequest getIdentityMailFromDomainAttributesRequest) {
        return null;
    }

    @Override
    public GetIdentityNotificationAttributesResult getIdentityNotificationAttributes(GetIdentityNotificationAttributesRequest getIdentityNotificationAttributesRequest) {
        return null;
    }

    @Override
    public GetIdentityPoliciesResult getIdentityPolicies(GetIdentityPoliciesRequest getIdentityPoliciesRequest) {
        return null;
    }

    @Override
    public GetIdentityVerificationAttributesResult getIdentityVerificationAttributes(GetIdentityVerificationAttributesRequest getIdentityVerificationAttributesRequest) {
        return null;
    }

    @Override
    public GetSendQuotaResult getSendQuota(GetSendQuotaRequest getSendQuotaRequest) {
        return null;
    }

    @Override
    public GetSendQuotaResult getSendQuota() {
        return null;
    }

    @Override
    public GetSendStatisticsResult getSendStatistics(GetSendStatisticsRequest getSendStatisticsRequest) {
        return null;
    }

    @Override
    public GetSendStatisticsResult getSendStatistics() {
        return null;
    }

    @Override
    public GetTemplateResult getTemplate(GetTemplateRequest getTemplateRequest) {
        return null;
    }

    @Override
    public ListConfigurationSetsResult listConfigurationSets(ListConfigurationSetsRequest listConfigurationSetsRequest) {
        return null;
    }

    @Override
    public ListCustomVerificationEmailTemplatesResult listCustomVerificationEmailTemplates(ListCustomVerificationEmailTemplatesRequest listCustomVerificationEmailTemplatesRequest) {
        return null;
    }

    @Override
    public ListIdentitiesResult listIdentities(ListIdentitiesRequest listIdentitiesRequest) {
        return null;
    }

    @Override
    public ListIdentitiesResult listIdentities() {
        return null;
    }

    @Override
    public ListIdentityPoliciesResult listIdentityPolicies(ListIdentityPoliciesRequest listIdentityPoliciesRequest) {
        return null;
    }

    @Override
    public ListReceiptFiltersResult listReceiptFilters(ListReceiptFiltersRequest listReceiptFiltersRequest) {
        return null;
    }

    @Override
    public ListReceiptRuleSetsResult listReceiptRuleSets(ListReceiptRuleSetsRequest listReceiptRuleSetsRequest) {
        return null;
    }

    @Override
    public ListTemplatesResult listTemplates(ListTemplatesRequest listTemplatesRequest) {
        return null;
    }

    @Override
    public ListVerifiedEmailAddressesResult listVerifiedEmailAddresses(ListVerifiedEmailAddressesRequest listVerifiedEmailAddressesRequest) {
        return null;
    }

    @Override
    public ListVerifiedEmailAddressesResult listVerifiedEmailAddresses() {
        return null;
    }

    @Override
    public PutConfigurationSetDeliveryOptionsResult putConfigurationSetDeliveryOptions(PutConfigurationSetDeliveryOptionsRequest putConfigurationSetDeliveryOptionsRequest) {
        return null;
    }

    @Override
    public PutIdentityPolicyResult putIdentityPolicy(PutIdentityPolicyRequest putIdentityPolicyRequest) {
        return null;
    }

    @Override
    public ReorderReceiptRuleSetResult reorderReceiptRuleSet(ReorderReceiptRuleSetRequest reorderReceiptRuleSetRequest) {
        return null;
    }

    @Override
    public SendBounceResult sendBounce(SendBounceRequest sendBounceRequest) {
        return null;
    }

    @Override
    public SendBulkTemplatedEmailResult sendBulkTemplatedEmail(SendBulkTemplatedEmailRequest sendBulkTemplatedEmailRequest) {
        return null;
    }

    @Override
    public SendCustomVerificationEmailResult sendCustomVerificationEmail(SendCustomVerificationEmailRequest sendCustomVerificationEmailRequest) {
        return null;
    }

    @Override
    public SendRawEmailResult sendRawEmail(SendRawEmailRequest sendRawEmailRequest) {
        return null;
    }

    @Override
    public SendTemplatedEmailResult sendTemplatedEmail(SendTemplatedEmailRequest sendTemplatedEmailRequest) {
        return null;
    }

    @Override
    public SetActiveReceiptRuleSetResult setActiveReceiptRuleSet(SetActiveReceiptRuleSetRequest setActiveReceiptRuleSetRequest) {
        return null;
    }

    @Override
    public SetIdentityDkimEnabledResult setIdentityDkimEnabled(SetIdentityDkimEnabledRequest setIdentityDkimEnabledRequest) {
        return null;
    }

    @Override
    public SetIdentityFeedbackForwardingEnabledResult setIdentityFeedbackForwardingEnabled(SetIdentityFeedbackForwardingEnabledRequest setIdentityFeedbackForwardingEnabledRequest) {
        return null;
    }

    @Override
    public SetIdentityHeadersInNotificationsEnabledResult setIdentityHeadersInNotificationsEnabled(SetIdentityHeadersInNotificationsEnabledRequest setIdentityHeadersInNotificationsEnabledRequest) {
        return null;
    }

    @Override
    public SetIdentityMailFromDomainResult setIdentityMailFromDomain(SetIdentityMailFromDomainRequest setIdentityMailFromDomainRequest) {
        return null;
    }

    @Override
    public SetIdentityNotificationTopicResult setIdentityNotificationTopic(SetIdentityNotificationTopicRequest setIdentityNotificationTopicRequest) {
        return null;
    }

    @Override
    public SetReceiptRulePositionResult setReceiptRulePosition(SetReceiptRulePositionRequest setReceiptRulePositionRequest) {
        return null;
    }

    @Override
    public TestRenderTemplateResult testRenderTemplate(TestRenderTemplateRequest testRenderTemplateRequest) {
        return null;
    }

    @Override
    public UpdateAccountSendingEnabledResult updateAccountSendingEnabled(UpdateAccountSendingEnabledRequest updateAccountSendingEnabledRequest) {
        return null;
    }

    @Override
    public UpdateConfigurationSetEventDestinationResult updateConfigurationSetEventDestination(UpdateConfigurationSetEventDestinationRequest updateConfigurationSetEventDestinationRequest) {
        return null;
    }

    @Override
    public UpdateConfigurationSetReputationMetricsEnabledResult updateConfigurationSetReputationMetricsEnabled(UpdateConfigurationSetReputationMetricsEnabledRequest updateConfigurationSetReputationMetricsEnabledRequest) {
        return null;
    }

    @Override
    public UpdateConfigurationSetSendingEnabledResult updateConfigurationSetSendingEnabled(UpdateConfigurationSetSendingEnabledRequest updateConfigurationSetSendingEnabledRequest) {
        return null;
    }

    @Override
    public UpdateConfigurationSetTrackingOptionsResult updateConfigurationSetTrackingOptions(UpdateConfigurationSetTrackingOptionsRequest updateConfigurationSetTrackingOptionsRequest) {
        return null;
    }

    @Override
    public UpdateCustomVerificationEmailTemplateResult updateCustomVerificationEmailTemplate(UpdateCustomVerificationEmailTemplateRequest updateCustomVerificationEmailTemplateRequest) {
        return null;
    }

    @Override
    public UpdateReceiptRuleResult updateReceiptRule(UpdateReceiptRuleRequest updateReceiptRuleRequest) {
        return null;
    }

    @Override
    public UpdateTemplateResult updateTemplate(UpdateTemplateRequest updateTemplateRequest) {
        return null;
    }

    @Override
    public VerifyDomainDkimResult verifyDomainDkim(VerifyDomainDkimRequest verifyDomainDkimRequest) {
        return null;
    }

    @Override
    public VerifyDomainIdentityResult verifyDomainIdentity(VerifyDomainIdentityRequest verifyDomainIdentityRequest) {
        return null;
    }

    @Override
    public VerifyEmailAddressResult verifyEmailAddress(VerifyEmailAddressRequest verifyEmailAddressRequest) {
        return null;
    }

    @Override
    public VerifyEmailIdentityResult verifyEmailIdentity(VerifyEmailIdentityRequest verifyEmailIdentityRequest) {
        return null;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return null;
    }

    @Override
    public AmazonSimpleEmailServiceWaiters waiters() {
        return null;
    }
}
