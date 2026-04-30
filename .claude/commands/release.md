You are preparing a release for the ViewDistanceControl PaperMC plugin. Do the following steps in order.

## Step 1 — Determine the commit range

Run `git tag --sort=-version:refname | head -1` to find the latest tag.

- If a tag exists, the range is `<tag>..HEAD`. Run `git log --oneline <tag>..HEAD` to list unreleased commits.
- If no tag exists, the range is the full history. Run `git log --oneline` instead.

If there are no commits beyond the latest tag, tell the user there is nothing to release and stop.

## Step 2 — Summarise the changes

Read the full diff for the commit range (`git diff <tag>..HEAD` or `git diff HEAD` if no tag) to understand exactly what
changed. Group the commits into these categories (omit empty categories):

- **Added** — new features or config options
- **Changed** — behaviour changes or refactors
- **Fixed** — bug fixes
- **Removed** — deleted features or options

Write clear, user-facing bullet points (not raw commit messages). One bullet per logical change; combine trivial
housekeeping commits.

## Step 3 — Update RELEASE_NOTES.md

Open `RELEASE_NOTES.md` at the project root (create it if absent). Prepend a new section at the top:

```
## overview

[A short and concise paragraph about this release.]

### Added
- …

### Changed
- …

### Fixed
- …

### Removed
- …
```

Only include sections that have content. Do not modify any existing sections below the new one.

## Step 4 — Code audit

Review all Java source files under `src/main/java/` for obvious issues. Check for:

- Logic bugs (off-by-one, wrong boolean conditions, null-pointer risks)
- Thread-safety problems (fields accessed from both scheduler callbacks and main thread without synchronisation)
- Resource leaks (tasks scheduled but never cancelled on plugin disable)
- Inconsistencies between `config.yml` defaults and `ConfigManager` hard-coded fallbacks
- Unused imports or fields
- Any `// TODO` or `// FIXME` comments that indicate unfinished work

Report findings as a numbered list. For each issue include: file, approximate line, description, and a suggested fix. If
no issues are found, say so explicitly. Check if README.md is up-to-date.

## Step 5 — Report

Print a summary:

1. The commit range inspected.
2. The release note section that was written.
3. The audit findings (or "No issues found.").