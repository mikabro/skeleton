package controllers;

import api.ReceiptResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


import dao.ReceiptDao;
import dao.TagsDao;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;



@Path("/tags")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final TagsDao tags;
    final ReceiptDao receipt;


    public TagController(TagsDao tags, ReceiptDao receipt) {
        this.tags = tags;
        this.receipt = receipt;
    }

    @PUT
//    @Consumes(MediaType.TEXT_PLAIN)
//    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{tag}")
    public String toggleTag(@PathParam("tag") String tag, int tid) {
        System.out.println("========================="+tag);
        if (tags.taggedReceipts(tag, tid)) {
            tags.delete(tag, tid);
        } else {
            tags.insert(tag, tid);
        }
        return tag;
    }

    @GET
    @Path("/{tag}")
    public List<ReceiptResponse> getReceipts(@PathParam("tag") String tag) {
        List<Integer> receipt_id = tags.getTagsWithReceipts(tag);
        System.out.println(receipt_id);
        List<ReceiptsRecord> tagsRecords = receipt.getAllReceiptsIds(receipt_id);
        System.out.println(tagsRecords);
        return tagsRecords.stream().map(ReceiptResponse::new).collect(toList());
    }
}