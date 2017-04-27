## Code of Conduct

This project adheres to the [Contributor Covenant](CODE_OF_CONDUCT.md) code of conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior to mateos.c19@gmail.com

## Take Your First Steps

### Understand the basics

Not sure what a pull request is, or how to submit one? Take a look at GitHub's excellent [help documentation](https://help.github.com/categories/collaborating-with-issues-and-pull-requests/) first.

### Search JIRA; create an issue if necessary

Is there already an issue that addresses your concern? Do a bit of searching in our [JIRA issue tracker](https://eversky.atlassian.net/) to see if you can find something similar. If you do not find something similar, please create a new JIRA issue before submitting a pull request unless the change is truly trivial -- for example: typo fixes, removing compiler warnings, etc.

## Create a Branch

### Branch from master

Master currently represents work toward next version. Please submit all pull requests there, even bug fixes and minor improvements.

### Use short branch names

Branches used when submitting pull requests should preferably be named according to JIRA issues, e.g. 'ROL-1234'. Otherwise, use succinct, lower-case, dash (-) delimited names, such as 'fix-warnings', 'fix-typo', etc. In [fork-and-edit](https://github.com/blog/844-forking-with-the-edit-button) cases, the GitHub default 'patch-1' is fine as well. This is important, because branch names show up in the merge commits that result from accepting pull requests and should be as expressive and concise as possible.

## Use Project Code Style

The complete Project Code Style reference is not available now, but here's a quick summary:

### Mind the whitespace

Please carefully follow the whitespace and formatting conventions already present in the framework.

1. Tabs, not spaces
1. Unix (LF), not DOS (CRLF) line endings
1. Eliminate all trailing whitespace
1. Wrap Javadoc at 90 characters
1. Aim to wrap code at 90 characters, but favor readability over wrapping
1. Preserve existing formatting; i.e. do not reformat code for its own sake
1. Search the codebase using git grep and other tools to discover common naming conventions, etc.
1. UTF-8 encoding for Java sources

### Add Apache license header to all new classes

```java
/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ...;
```

### Update Apache license header in modified files as necessary

Always check the date range in the license header. For example, if you've modified a file in 2017 whose header still reads:

```java
/*
 * Copyright 2002-2011 the original author or authors.
```

Then be sure to update it to 2017 accordingly:

```java
/*
 * Copyright 2002-2017 the original author or authors.
```

### Use @since tags for newly-added public API types and methods

For example:

```java
/**
 * ...
 *
 * @author First Last
 * @since 5.0
 * @see ...
 */
```
