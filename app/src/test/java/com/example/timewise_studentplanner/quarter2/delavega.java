package com.example.timewise_studentplanner.quarter2.Practical3;

/**
 * Manages a dynamic collection of {@link Account} objects.
 * <p>
 * The internal array grows automatically (doubling strategy) when capacity is exceeded.
 * This class is intentionally package-private.
 */
class Records {

    private static final int INITIAL_CAPACITY = 10;

    private Account[] accounts;
    private int accountCount;

    /**
     * Creates an empty Records collection with an initial capacity of 10.
     */
    public Records() {
        this.accounts = new Account[INITIAL_CAPACITY];
        this.accountCount = 0;
    }

    /**
     * Adds an account to the collection.
     * Automatically expands the internal array if it is full.
     *
     * @param account the account to add (must not be {@code null})
     * @throws IllegalArgumentException if {@code account} is {@code null}
     */
    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        ensureCapacity(accountCount + 1);
        accounts[accountCount++] = account;
    }

    /**
     * Finds the first account whose student name matches the given name
     * (case-insensitive).
     *
     * @param name the student name to search for
     * @return the matching {@link Account}, or {@code null} if not found
     */
    public Account findAccount(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getStudentName().equalsIgnoreCase(name.trim())) {
                return accounts[i];
            }
        }
        return null;
    }

    /**
     * Returns the current number of accounts stored.
     *
     * @return the number of accounts
     */
    public int getAccountCount() {
        return accountCount;
    }

    /**
     * Returns a defensive copy of the accounts currently stored.
     *
     * @return a new array containing the accounts (never {@code null})
     */
    public Account[] getAccounts() {
        Account[] copy = new Account[accountCount];
        System.arraycopy(accounts, 0, copy, 0, accountCount);
        return copy;
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= accounts.length) {
            return;
        }

        int newCapacity = accounts.length * 2;
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }

        Account[] newArray = new Account[newCapacity];
        System.arraycopy(accounts, 0, newArray, 0, accountCount);
        accounts = newArray;
    }
}