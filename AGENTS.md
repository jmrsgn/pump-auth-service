# Pump Auth Service — Agent Guide

## Repository responsibility

- This service owns Pump's authentication and account identity domain:
  local credentials, authentication state, JWT issuance/validation,
  and account-verification flows: local credentials,
  user identity records, JWT issuance/validation, and account-verification flows.
- It is one service in a microservice architecture. Keep its APIs focused on
  identity; do not add social, coaching, gateway, or client-domain behavior here.
- The service may retrieve profile information from Pump Social through its
  explicit client boundary. Treat that service as the owner of social-profile data.

## Before changing code

- Inspect the relevant controller, service, repository, DTO, configuration, and
  existing tests before proposing or making a change.
- Follow established package and response conventions: controllers delegate to
  services; services own use-case logic; repositories own persistence access.
- Reuse the project's DTOs, error types, `Result` response wrapper, constants,
  logging utilities, and configuration properties where appropriate.
- Keep changes narrowly scoped to the requested outcome. Do not make unrelated
  refactors, formatting sweeps, dependency upgrades, or generated-file changes.
- If requirements, upstream contracts, deployment details, or security policy are
  absent, state the uncertainty and ask or make a clearly labelled assumption;
  never invent missing facts.

## Technology and architecture

- Target Java 17 and Maven with Spring Boot. Use the Maven wrapper when running
  project commands.
- The application is layered as HTTP controllers, application services, JPA
  repositories/entities, and outbound service clients, with security,
  configuration, events/listeners, and exception handling as cross-cutting code.
- Prefer constructor injection and Spring configuration/property binding over
  static configuration, hidden environment reads, or manual object construction.
- Preserve API compatibility unless the task explicitly authorizes a contract
  change. Validate request DTOs and return consistent API error responses.

## Ownership and data boundaries

- This service owns its authentication database and the identity data it stores:
  users, credential hashes, roles/authorization data it manages, and verification
  tokens. Do not access another service's database or duplicate its owned data.
- Use Spring Data JPA repositories for persistence. Make schema/data changes
  deliberately and account for the configured PostgreSQL environments.
- Treat database migrations, retention, uniqueness, and referential behavior as
  production concerns. Do not rely on development-only schema generation as a
  deployment strategy without explicit direction.
- Keep outbound calls behind the relevant client/configuration boundary. Propagate
  correlation identifiers and use the configured internal-service authentication
  mechanism for service-to-service calls.

## Security and privacy

- Never log, commit, or expose passwords, JWTs, verification tokens, SMTP
  credentials, database credentials, or internal-service tokens.
- Store passwords only as strong one-way hashes; use the configured password
  encoder rather than custom cryptography.
- Preserve JWT signature, expiry, and authentication checks. New protected
  endpoints must have an explicit authorization decision.
- Model and enforce roles/authorities consistently when authorization is required;
  do not trust caller-supplied identity or privilege headers.
- Keep secrets externalized through environment/configuration injection. Do not
  place real values in resource files, tests, Dockerfiles, or documentation.

## Testing and verification

- Add or update focused tests for behavior changes. Cover success, validation,
  authorization, failure, and boundary cases in proportion to the change.
- Use Spring Security test support for security behavior and isolate external
  services and mail delivery in tests; tests must not call real dependencies.
- Run the smallest relevant Maven test suite before handoff when the environment
  permits. Report commands run and any tests that could not be run.
- Keep the application context test healthy and do not weaken security merely to
  make a test pass.

## Operations and production readiness

- Keep configuration profile-aware (`dev`, `docker`, `k8s`) and make new required
  settings explicit. Do not assume a profile is active unless configuration says so.
- Preserve health/probe behavior, graceful shutdown settings, and correlation-ID
  propagation. Add meaningful structured logs without sensitive data.
- Keep the Docker build reproducible and aligned with the Java/Maven runtime requirements.
- Before changing CI/CD behavior, inspect the repository's existing workflows
  and deployment configuration. If those are managed elsewhere, state that
  dependency explicitly rather than assuming their implementation.
- Do not add deployment resources, infrastructure changes, or external-service
  contract changes unless the task explicitly includes them.

## Handoff

- Explain significant design decisions, tradeoffs, API/security implications, and
  configuration or migration impact in the final response.
- Clearly list files changed and verification performed. Call out remaining
  uncertainty, follow-up work, and any assumptions that affect correctness.
