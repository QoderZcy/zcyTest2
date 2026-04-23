# Conventional Commits Specification

## Format

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

## Types

| Type | Description |
|------|-------------|
| feat | New feature |
| fix | Bug fix |
| docs | Documentation changes |
| style | Code style changes (formatting, no code change) |
| refactor | Code refactoring |
| perf | Performance improvements |
| test | Adding/updating tests |
| build | Build system changes |
| ci | CI configuration changes |
| chore | Other changes |
| revert | Reverting previous commit |

## Examples

```
feat(auth): add JWT token validation
fix(api): handle null response from endpoint
docs: update README with setup instructions
refactor(database): simplify connection pooling
test(user): add unit tests for registration flow
```

## Best Practices

1. Use imperative mood: "add" not "added" or "adds"
2. Don't capitalize first letter after type
3. Don't end with period
4. Keep subject line under 72 characters
5. Separate body from subject with blank line
6. Use body to explain what/why, not how