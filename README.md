# Some Nights At Whatever

A few years back, I became interested in the Five Nights at Freddy's fangame scene. A lot of these games are made in Clickteam, which is not a game-making tool. I was curious if I could program a framework for a Five Nights at Freddy's game-making tool in Java + LibGDX more quickly than making a game in Clickteam would take.

The answer was "yes", and this framework is at around feature parity with FNAF 1/FNAF 3. However, it's a little bit of a programmatic nightmare - it takes script files that are loaded into massive binary trees of functional interfaces in RAM, which is neither efficient nor sensible. However, I'm proud of the general effort that went into it and the comfort level it gave me with functional interfaces, so I've put this on GitHub in case I ever do want to return to it (or in case someone else is interested in it.) It was a good way to pass some time during the COVID pandemic, but I haven't returned to it since. 

SNAW is licensed under the GNU GPL. It comes with a test game, "sampleFile.snaw", which is covered under the same license. 
