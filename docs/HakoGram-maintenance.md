# HakoGram maintenance and Telegram upstream updates

The upstream baseline is Telegram Android `13.0.3 (7183)` (`0aae959`). The configured Git remote `origin` remains the user's repository (`https://github.com/mritch1313/HakoGram.git`); no upstream remote is configured in this checkout. Do not change `origin` when adding an upstream for comparison.

## Updating from Telegram

1. Fetch DrKLO/Telegram into a separate remote/ref (do not push to it): `git remote add upstream https://github.com/DrKLO/Telegram.git` once, then `git fetch upstream`.
2. Review upstream tags/commits and compare the selected Telegram base with the current base using `git log` and `git diff` before merging.
3. Integrate upstream into a dedicated update branch based on this HakoGram branch, resolve conflicts, and keep HakoGram-only changes separate and small.
4. Review the files listed in the HakoGram-specific commits, run the GitHub Actions build, and merge only after review. Never force-push over shared history.

The current local contribution branch is `arena/747bd099-hakogram`; keep changes there. Upstream update remains a manual, reviewed process.
