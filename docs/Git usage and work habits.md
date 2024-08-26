# Git workflow and habits

The most important reason for adopting a set git workflow for our team is to ensure a smooth and time efficient process. This means we avoid ambiguities, which in turn means avoiding confusion and potensial merge conflicts.

## Master branch

The master branch is considered holy and must not be touched for other means than for merging in working branches. That means no working directly in it or pushing from a local version. This is the branch where all the other branches come out of in order to prevent conflicts. The master branch should always contain working and deployable code.

## New branches

When working on any new code, a new branch should be made from master by using the issues-feature on GitLab. Each branch will be aptly named to describe the purpose and automatically given a number, one higher than the last branch. You should have pulled the latest from master, and always branch from the place it will be merged back into. In our case that will most likely be the master branch.  
In order to organize branches, we have used milestones and issues.

## Milestones

For each new release, a new milestone is made in GitLab. These are simply numbered and contain every branch, issue and new code for their respective release. They are also given a due date according to project deadlines.

## Issues

Under each milestone, you will find issues. These are the tasks that are being solved with each release and all represent a new branch. While milestones create an overview of all overarching issues to be solved, issues create overview of all individual commits and code updates within that same issue. That is, if they are named well.

Issues should have a short, descriptive names and you should rather create a new issue than make a complex one. Although you want to make sure to not create any merge conflicts by being too narrow either.
Our practice is creating new issues for anything that does not overlap with any on going issue, in such a way that we at least are never working in the same files. In addition to having short names though, you will also add a description in the issue a a sentence or two. These define more in depth what the task is meant to achieve for more clarity.

## Merge requests/working branches

These are the branches that are made by creating a merge request from an issue. This is a great practice in order to both branch out and create the path for merging in to the same branch again. This in itself prevents merge conflicts.

In contrast to the master branch, these do not have to be working. They do however have to be assigned to a person on the team, and at least one reviewer. This person(s) is tasked with reviewing the code as an extra step to assure good code and work practice. If they do not approve, the files will be reworked, otherwise they will make sure everything is complete and safe to merge and approve the mergw in gitlab. Only then can the assigned person merge their branch and close the issue.
Whenever an issue is closed, progress can be tracked for the milestone we are working on to get an overview.  

Progress in gitlab is based on percent and number of tasks, and often do not take into account the difference in difficulty. Therefore scheduling is often adapted since issues are made for being logical branches, not to be equal in time. Read more about work habits and divising tasks under "work habits".

## Work habits

### Pair programming

While git is our way of structuring work into branches and issues, we have implemented some habits to help our workflow in getting that work done.
The most intergral part is the implementation of pair programming and inspections. In short, this means working together in pairs on the same code, on just one computer.  
This does not however, mean that one strictly codes and the other does not, but switching frequently.  
One will be writing and mainly working on new code, whilst the other observes, advices and bounces ideas for the best possible code.

We have used this to solve problems along the way and think far ahead thoughout the process. We have different strengths that we utilize within the team, which we can draw even more advantages from with pair programming. Our experience is that these habits make it more natural to collaborate and increases the quality of our code over all.  
In some cases it also saves time when we discover problems more often, have two sets of eyes to discover discrepancies and more creative flow.

When dividing tasks for a new release, we consider who might be best equipped for main responsibility and split tasks. This obviously includes considering pair programming, possibly time consuming reviewing and often times the initiator is assigned. Still, everyone might have relevant ideas for new functionality and improvements and ends up working in that branch or advising along the gitlab-reviewer.

### Code quality

In order to improve our code quality we need an overview of how good it is at any given time. To solve this we have implemented Jacoco (Java Code Coverage) which measures our code coverage and provides a visual report.

These tools helps us regularly check our code quality, and gives way more feedback than regular errors to facilitate a solution and improve our code.
To read more about checkstyle and spotbugs, see [configuration](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2221/gr2221/-/tree/master/eventplanner/config)
