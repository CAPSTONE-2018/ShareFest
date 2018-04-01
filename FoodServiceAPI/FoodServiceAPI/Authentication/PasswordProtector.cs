using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using System.Security.Cryptography;
using System.Text;

namespace FoodServiceAPI.Authentication
{
    public class PasswordProtector
    {
        const int SALT_NUM_BYTES = 8;

        public byte[] Protect(string plain)
        {
            // Salt password
            byte[] salt = new byte[SALT_NUM_BYTES];

            using(RandomNumberGenerator rng = RandomNumberGenerator.Create())
                rng.GetBytes(salt);

            // Hash salted password
            byte[] hash = Hash(AppendSalt(plain, salt));

            // Append salt to hash result
            byte[] hashWithSalt = new byte[hash.Length + salt.Length];

            hash.CopyTo(hashWithSalt, 0);
            salt.CopyTo(hashWithSalt, hash.Length);

            return hashWithSalt;
        }

        public bool Compare(string plain, byte[] hashWithSalt)
        {
            if (hashWithSalt.Length <= SALT_NUM_BYTES)
                throw new Exception("hashWithSalt is too short to contain a hash with a salt");

            // Separate hash and salt
            byte[] hash = new byte[hashWithSalt.Length - SALT_NUM_BYTES];
            byte[] salt = new byte[SALT_NUM_BYTES];

            Array.Copy(hashWithSalt, 0, hash, 0, hash.Length);
            Array.Copy(hashWithSalt, hashWithSalt.Length - SALT_NUM_BYTES, salt, 0, SALT_NUM_BYTES);

            // Salt and hash login password
            byte[] loginHash = Hash(AppendSalt(plain, salt));

            // Compare
            if (hash.Length != loginHash.Length)
                throw new Exception("Length of given hash does not match length of generated hash");

            for(int i = 0; i < hash.Length; i++)
            {
                if (hash[i] != loginHash[i])
                    return false;
            }

            return true;
        }

        protected byte[] AppendSalt(string plain, byte[] salt)
        {
            byte[] plainBytes = Encoding.UTF8.GetBytes(plain);
            byte[] plainWithSalt = new byte[plainBytes.Length + salt.Length];

            plainBytes.CopyTo(plainWithSalt, 0);
            salt.CopyTo(plainWithSalt, plainBytes.Length);

            return plainWithSalt;
        }

        protected byte[] Hash(byte[] bytes)
        {
            using(SHA256Managed alg = new SHA256Managed())
                return alg.ComputeHash(bytes);
        }
    }
}
