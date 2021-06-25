<img src="icon.png" align="left" />

# Algorithm
## Step-1
Choose two prime number p and q
Lets take p = 3 and q = 11

## Step-2
Compute the value of n and \phi
It is given as,
n = p \times q and \phi = (p-1) \times (q-1)

Here in the example,
n = 3 \times 11 = 33
\phi = (3-1) \times (11-1) = 2 \times 10 = 20

## Step-3
Find the value of e (public key)
Choose e, such that e should be co-prime. Co-prime means it should not
multiply by factors of \phi and also not divide by \phi

Factors of \phi are, 20 = 5 \times 4 = 5 \times 2 \times 2 so e should not
multiply by 5 and 2 and should not divide by 20.

So, primes are 3, 7, 11, 17, 19…, as 3 and 11 are taken choose e as 7

Therefore, e = 7

Step-4: Compute the value of d (private key)
The condition is given as,
gcd(\phi, e) = \phi x +ey = 1 where y is the value of d.
To compute the value of d,

Form a table with four columns i.e., a, b, d, and k.
Initialize a = 1, b = 0, d = \phi, k = – in first row.
Initialize a = 0, b = 1, d = e, k = \frac{\phi}{e} in second row.

From the next row, apply following formulas to find the value of next
a, b, d, and k, which is given as;
a_{i} = a_{i-2} - (a_{i-1} \times k_{i-1})
b_{i} = b_{i-2} - (b_{i-1} \times k_{i-1})
d_{i} = d_{i-2} - (d_{i-1} \times k_{i-1})
k_{i} = \frac{d_{i-1}}{d_{i}}

As soon as, d = 1, stop the process and check for the below condition

if b > \phi
    b = b \mod \phi
if b < 0
    b = b + \phi

For a given example, the table will be,

 A	 B	D	K
 1	 0	20	–
 0	 1	7	2
 1	-2	6	1
-1	 3	1	–

As in the above table d = 1, stop the process and check for the condition given for the b
\therefore b = 3 To verify that b is correct, the above condition should satisfy, i.e.
gcd(\phi, e) = \phi x + ey = (20 \times -1) + (7 \times 3) = 1. Hence d is correct.

Step-5: Do the encryption and decryption
Encryption is given as, c = t^{e}\mod n
Decryption is given as, t = c^{d}\mod n

For the given example, suppose t = 2, so;

Encryption is c = 2^{7}\mod 33 = 29
Decryption is t = 29^{3}\mod 33 = 2

Therefore in the final, p = 3, q = 11, \phi = 20, n = 33, e = 7 and d = 3

Sakarya University - Computer Engineering - Cryptography Class
