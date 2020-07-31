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

    private final HashMap<UUID, Double> playerBank = new HashMap<>();

    public void load() {
        playerBank.clear();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return getPl().getDescription().getName();
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
        OfflinePlayer player = Bukkit.getOfflinePlayer(s);
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
        OfflinePlayer player = Bukkit.getOfflinePlayer(s);
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
        OfflinePlayer player = Bukkit.getOfflinePlayer(s);
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
        OfflinePlayer player = Bukkit.getOfflinePlayer(s);
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
        OfflinePlayer player = Bukkit.getOfflinePlayer(s);
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
        OfflinePlayer player = Bukkit.getOfflinePlayer(s);
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
        OfflinePlayer p = Bukkit.getOfflinePlayer(s);
        return withdrawPlayerCheck(p, amt);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, double amt) {
        return withdrawPlayerCheck(p, amt);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double amt) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(s);
        return withdrawPlayerCheck(p, amt);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, String s, double amt) {
        return withdrawPlayerCheck(p, amt);
    }

    private EconomyResponse withdrawPlayerCheck(OfflinePlayer p, double amt) {
        if (getBalance(p) - amt >= 0) {
            UUID id = p.getUniqueId();
            playerBank.put(id, playerBank.get(id) - amt);
            return responseSuccess(amt, getBalance(p));
        } else
            return responseFailed(amt, getBalance(p));
    }

    public void setPlayer(OfflinePlayer p, double amt) {
        playerBank.put(p.getUniqueId(), amt);
    }

    /*private void checkPlayer(UUID id) {
        if (playerBank.getOrDefault(id, 0.0) < 0.0)
            playerBank.put(id, 0.0);
    }*/

    @Override
    public EconomyResponse depositPlayer(String s, double amt) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.get(id) + amt);
        return responseSuccess(amt, playerBank.get(id));
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amt) {
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.getOrDefault(id, 0.0) + amt);
        return responseSuccess(amt, playerBank.get(id));
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double amt) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.getOrDefault(id, 0.0) + amt);
        return responseSuccess(amt, playerBank.get(id));
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String s, double amt) {
        UUID id = player.getUniqueId();
        playerBank.put(id, playerBank.getOrDefault(id, 0.0) + amt);
        return responseSuccess(amt, playerBank.get(id));
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
        playerBank.put(id, 0.0);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        UUID id = player.getUniqueId();
        playerBank.put(id, 0.0);
        return true;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        playerBank.put(id, 0.0);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String s) {
        UUID id = player.getUniqueId();
        playerBank.put(id, 0.0);
        return true;
    }

    private EconomyResponse responseSuccess(double amountChange, double balance) {
        return new EconomyResponse(amountChange, balance, EconomyResponse.ResponseType.SUCCESS, null);
    }

    private EconomyResponse responseFailed(double amountChange, double balance) {
        return new EconomyResponse(amountChange, balance, EconomyResponse.ResponseType.FAILURE, null);
    }
    
    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
