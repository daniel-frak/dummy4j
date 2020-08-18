# Contributing guidelines #

Thank you for considering contributing to this project! There's only a few simple rules to follow.

## Everything happens on Github
This project uses Github to host code, to track issues and feature requests, as well as accept pull requests.

## All discussion is welcome

This project can only meaningfully grow through community feedback.
Consider [opening an issue](https://github.com/daniel-frak/dummy4j/issues/new/choose) if you find a bug, want to
request (or propose) a feature or simply want to discuss something related to it.

## Write bug reports with detail, background, and sample code

**Great Bug Reports** tend to have:

- A quick summary and/or background
- Steps to reproduce
  - Be specific!
  - Give sample code if you can.
- What you expected would happen
- What actually happens
- Notes (possibly including why you think this might be happening, or things you tried that didn't work)

## We use [Github Flow](https://guides.github.com/introduction/flow/index.html), so all changes happen through Pull Requests

Pull Requests are the best way to propose changes to the codebase.
To create a Pull Request:

1. Fork the repo and create your branch from `master`.
2. Make sure all code you have added has corresponding tests.
3. If you have changed APIs, update the documentation.
4. Ensure the test suite passes.
5. Issue that pull request!

Once you have opened the Pull Request, your code will be analyzed by SonarQube.
You should fix all bugs and code smells found by the analysis, as well as write the necessary tests if code coverage
is found to be below the quality gate. If you find that some of the reported issues are false positives, let the
reviewer know in the comments of the PR.

In the meantime, one of the maintainers will perform a review of your code and give you tips on what (if anything) 
should be improved.

When the code passes both the analysis and the code review, it will be merged into the `master` branch.
Thank you for your contribution!  

## Coding style

Try to follow the coding style prevalent throughout the codebase - consistency is best!
If you feel like doing things another way would be better for the project, feel free to open an issue to discuss your
concerns.

## Code coverage

We aim to have 100% code coverage and encourage Test Driven Development in order to achieve it. This means that any
code you write should have all possible paths tested.

## Any contributions you make will be under the MIT Software License
In short, when you submit code changes, your submissions are understood to be under the same 
[MIT License](http://choosealicense.com/licenses/mit/) that covers the project.
Feel free to contact the maintainers if that's a concern.