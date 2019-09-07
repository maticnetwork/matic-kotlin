package network.matic.matick.crypto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import network.matic.matick.rlp.RlpEncoder;
import network.matic.matick.rlp.RlpList;
import network.matic.matick.rlp.RlpString;
import network.matic.matick.rlp.RlpType;
import network.matic.matick.utils.crypto.Hash;
import network.matic.matick.utils.utils.Numeric;

/**
 * Smart Contract utility functions.
 */
public class ContractUtils {

    /**
     * Generate a smart contract address. This enables you to identify what address a
     * smart contract will be deployed to on the network.
     *
     * @param address of sender
     * @param nonce   of transaction
     * @return the generated smart contract address
     */
    public static byte[] generateContractAddress(byte[] address, BigInteger nonce) {
        List<RlpType> values = new ArrayList<>();

        values.add(RlpString.create(address));
        values.add(RlpString.create(nonce));
        RlpList rlpList = new RlpList(values);

        byte[] encoded = RlpEncoder.encode(rlpList);
        byte[] hashed = Hash.sha3(encoded);
        return Arrays.copyOfRange(hashed, 12, hashed.length);
    }

    public static String generateContractAddress(String address, BigInteger nonce) {
        byte[] result = generateContractAddress(Numeric.hexStringToByteArray(address), nonce);
        return Numeric.toHexString(result);
    }
}