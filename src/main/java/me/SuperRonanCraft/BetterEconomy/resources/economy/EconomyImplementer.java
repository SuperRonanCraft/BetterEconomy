package me.SuperRonanCraft.BetterEconomy.resources.economy;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EconomyImplementer implements Economy {

    private HashMap<UUID, Double> playerBank = new HashMap<>();

    public void load() {
        playerBank.clear();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return "Coins";
    }

    @Override
    public String currencyNameSingular() {
        return "Coin";
    }

    @Override
    public boolean hasAccount(String s) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        return playerBank.containsKey(id);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        UUID id = player.getUniqueId();
        return playerBank.containsKey(id);
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        return playerBank.containsKey(id);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String s) {
        UUID id = player.getUniqueId();
        return playerBank.containsKey(id);
    }

    @Override
    public double getBalance(String s) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        return playerBank.getOrDefault(id, 0.0);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        UUID id = player.getUniqueId();
        return playerBank.getOrDefault(id, 0.0);
    }

    @Override
    public double getBalance(String s, String s1) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        return playerBank.getOrDefault(id, 0.0);
    }

    @Override
    public double getBalance(OfflinePlayer player, String s) {
        UUID id = player.getUniqueId();
        return playerBank.getOrDefault(id, 0.0);
    }

    public void playerRemove(UUID id) {
        playerBank.remove(id);
    }

    @Override
    public boolean has(String s, double v) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        double amount = playerBank.getOrDefault(id,  0.0);
        return amount >= v;
    }

    @Override
    public boolean has(OfflinePlayer player, double v) {
        UUID id = player.getUniqueId();
        double amount = playerBank.getOrDefault(id,  0.0);
        return amount >= v;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        double amount = playerBank.getOrDefault(id,  0.0);
        return amount >= v;
    }

    @Override
    public boolean has(OfflinePlayer player, String s, double v) {
        UUID id = player.getUniqueId();
        double amount = playerBank.getOrDefault(id,  0.0);
        return amount >= v;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double amt) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.get(id) - amt);
        checkPlayer(player);
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amt) {
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.get(id) - amt);
        checkPlayer(player);
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double amt) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.get(id) - amt);
        checkPlayer(player);
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String s, double amt) {
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.get(id) - amt);
        checkPlayer(player);
        return null;
    }

    private void checkPlayer(OfflinePlayer p) {
        if (playerBank.getOrDefault(p.getUniqueId(), 0.0) < 0.0)
            playerBank.put(p.getUniqueId(), 0.0);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double amt) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.get(id) + amt);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amt) {
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.getOrDefault(id, 0.0) + amt);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double amt) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.getOrDefault(id, 0.0) + amt);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String s, double amt) {
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.getOrDefault(id, 0.0) + amt);
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        /*Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, 0.0);*/
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer player) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, 1.0);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        UUID id = player.getUniqueId();
        playerBank.put(id, 1.0);
        return true;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, 1.0);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String s) {
        UUID id = player.getUniqueId();
        playerBank.put(id, 1.0);
        return true;
    }
    
    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
