How to contribute 
=================

Thank you so much for wanting to contribute to the project! Here are a few
important things you should know about contributing:

  1. API changes require a pull request, discussion, use cases, etc.
  2. Pull requests are great for small fixes for bugs, documentation, etc.
  3. Pull requests are not merged directly into the master branch.
  3. Code contributions require signing a Contributor License Agreement. 

API changes 
-----------

We make changes to the public [API] of this project, including adding new APIs,
very carefully. Because of this, if you're interested in seeing a new feature
in the project, the best approach is to create an Pull Request (or comment on
an existing one, if there is one) with:

- specific use cases for it.
- API design to answer those use cases

If the feature has merit, it will go through a process of API design and
review. Code is welcomed but should come after this.

[APIs]: http://en.wikipedia.org/wiki/Application_programming_interface 
[issue]: https://github.com/google/guava/issues

Pull requests 
-------------

For a trivial fix such as for a typo or bugs, it's best to send a pull request
with code and explaining the fix.  

Some examples of types of pull requests that are immediately helpful:

  - Fixing a bug without changing a public API.
  - Fixing or improving documentation.
  - Improvements to Gradle configuration.

For non trivial change (eg: API changes),
create a pull request, explain the use case(s) for it and the proposed API
change, with full public classes/method/members documented

Guidelines for any code contributions:

  0. All pull requests must not fail when executing: `./gradlew check`
  1. All changes should be accompanied by tests. The project
  already has some test coverage, so look at some of the existing tests if
  you're unsure how to go about it.
  2. All contributions are subject to specific licensing terms. By submitting a Pull Request, you automatically:
      - Agree to be bound by the Individual or Corporate License agreement,
        whichever applies to your situation. See Contributor License Agreement below
        for details.
      - Agree to have your code licensed as per the project's licenses. 
        See LICENSES.md for licenses details.
  3. Files should be formatted according to Google's [Java style guide][].
  4. Please squash all commits for a change into a single commit (this can be
  done using `git rebase -i`). 
  5. Do your best to have a [well-formed commit message][] for the change.

[well-formed commit message]:
http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html

#### Merging pull requests ####

TBD(david)

Contributor License Agreement 
-----------------------------

Contributions to this project must be accompanied by a Contributor License
Agreement. This is not a copyright _assignment_; it simply gives project the
permission to use and redistribute your contributions as part of the project.

  - If you are an individual writing original source code and you're sure you
    own the intellectual property, then you'll need to sign the [individual
    CLA][]. Please include your GitHub username.
  - If you work for a company that wants to allow you to contribute your work,
    then you'll need to sign a [corporate CLA][].

You generally only need to submit a CLA once, so if you've already submitted
one (even if it was for a different project), you probably don't need to do it
again.

[individual CLA]: See section "Individual Contributor Agreement" in CONTRIBUTOR_LICENSE_AGREEMENTS.md 
[corporate CLA]: See section "Corporate Contributor Agreement" in CONTRIBUTOR_LICENSE_AGREEMENTS.md

Thanks to 
----------

This contribution guideline document is greatly inspired by Google Guava's
Contributions guidelines.
