/*
 * Copyright 2022, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */
package org.haiku.haikudepotserver.api2;

import com.google.common.base.Preconditions;
import org.haiku.haikudepotserver.api2.model.QueuePkgCategoryCoverageExportSpreadsheetJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgCategoryCoverageImportSpreadsheetJobRequestEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgCategoryCoverageImportSpreadsheetJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgIconArchiveImportJobRequestEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgIconArchiveImportJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgIconExportArchiveJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgIconSpreadsheetJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgLocalizationCoverageExportSpreadsheetJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgProminenceAndUserRatingSpreadsheetJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgScreenshotArchiveImportJobRequestEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgScreenshotArchiveImportJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgScreenshotExportArchiveJobRequestEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgScreenshotExportArchiveJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgScreenshotSpreadsheetJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgVersionLocalizationCoverageExportSpreadsheetJobResponseEnvelope;
import org.haiku.haikudepotserver.api2.model.QueuePkgVersionPayloadLengthPopulationJobResponseEnvelope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class PkgJobApiImpl extends AbstractApiImpl implements PkgJobApi {

    private final PkgJobApiService pkgJobApiService;

    public PkgJobApiImpl(PkgJobApiService pkgJobApiService) {
        this.pkgJobApiService = Preconditions.checkNotNull(pkgJobApiService);
    }

    @Override
    public ResponseEntity<QueuePkgCategoryCoverageExportSpreadsheetJobResponseEnvelope> queuePkgCategoryCoverageExportSpreadsheetJob(Object body) {
        return ResponseEntity.ok(
                new QueuePkgCategoryCoverageExportSpreadsheetJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgCategoryCoverageExportSpreadsheetJob()));
    }

    @Override
    public ResponseEntity<QueuePkgCategoryCoverageImportSpreadsheetJobResponseEnvelope> queuePkgCategoryCoverageImportSpreadsheetJob(QueuePkgCategoryCoverageImportSpreadsheetJobRequestEnvelope queuePkgCategoryCoverageImportSpreadsheetJobRequestEnvelope) {
        return ResponseEntity.ok(
                new QueuePkgCategoryCoverageImportSpreadsheetJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgCategoryCoverageImportSpreadsheetJob(queuePkgCategoryCoverageImportSpreadsheetJobRequestEnvelope)));
    }

    @Override
    public ResponseEntity<QueuePkgIconArchiveImportJobResponseEnvelope> queuePkgIconArchiveImportJob(QueuePkgIconArchiveImportJobRequestEnvelope queuePkgIconArchiveImportJobRequestEnvelope) {
        return ResponseEntity.ok(
                new QueuePkgIconArchiveImportJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgIconArchiveImportJob(queuePkgIconArchiveImportJobRequestEnvelope)));
    }

    @Override
    public ResponseEntity<QueuePkgIconExportArchiveJobResponseEnvelope> queuePkgIconExportArchiveJob(Object body) {
        return ResponseEntity.ok(
                new QueuePkgIconExportArchiveJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgIconExportArchiveJob()));
    }

    @Override
    public ResponseEntity<QueuePkgIconSpreadsheetJobResponseEnvelope> queuePkgIconSpreadsheetJob(Object body) {
        return ResponseEntity.ok(
                new QueuePkgIconSpreadsheetJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgIconSpreadsheetJob()));
    }

    @Override
    public ResponseEntity<QueuePkgLocalizationCoverageExportSpreadsheetJobResponseEnvelope> queuePkgLocalizationCoverageExportSpreadsheetJob(Object body) {
        return ResponseEntity.ok(
                new QueuePkgLocalizationCoverageExportSpreadsheetJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgLocalizationCoverageExportSpreadsheetJob()));
    }

    @Override
    public ResponseEntity<QueuePkgProminenceAndUserRatingSpreadsheetJobResponseEnvelope> queuePkgProminenceAndUserRatingSpreadsheetJob(Object body) {
        return ResponseEntity.ok(
                new QueuePkgProminenceAndUserRatingSpreadsheetJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgProminenceAndUserRatingSpreadsheetJob()));
    }

    @Override
    public ResponseEntity<QueuePkgScreenshotArchiveImportJobResponseEnvelope> queuePkgScreenshotArchiveImportJob(QueuePkgScreenshotArchiveImportJobRequestEnvelope queuePkgScreenshotArchiveImportJobRequestEnvelope) {
        return ResponseEntity.ok(
                new QueuePkgScreenshotArchiveImportJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgScreenshotArchiveImportJob(queuePkgScreenshotArchiveImportJobRequestEnvelope)));
    }

    @Override
    public ResponseEntity<QueuePkgScreenshotExportArchiveJobResponseEnvelope> queuePkgScreenshotExportArchiveJob(QueuePkgScreenshotExportArchiveJobRequestEnvelope queuePkgScreenshotExportArchiveJobRequestEnvelope) {
        return ResponseEntity.ok(
                new QueuePkgScreenshotExportArchiveJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgScreenshotExportArchiveJob(queuePkgScreenshotExportArchiveJobRequestEnvelope)));
    }

    @Override
    public ResponseEntity<QueuePkgScreenshotSpreadsheetJobResponseEnvelope> queuePkgScreenshotSpreadsheetJob(Object body) {
        return ResponseEntity.ok(
                new QueuePkgScreenshotSpreadsheetJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgScreenshotSpreadsheetJob()));
    }

    @Override
    public ResponseEntity<QueuePkgVersionLocalizationCoverageExportSpreadsheetJobResponseEnvelope> queuePkgVersionLocalizationCoverageExportSpreadsheetJob(Object body) {
        return ResponseEntity.ok(
                new QueuePkgVersionLocalizationCoverageExportSpreadsheetJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgVersionLocalizationCoverageExportSpreadsheetJob()));
    }

    @Override
    public ResponseEntity<QueuePkgVersionPayloadLengthPopulationJobResponseEnvelope> queuePkgVersionPayloadLengthPopulationJob(Object body) {
        return ResponseEntity.ok(
                new QueuePkgVersionPayloadLengthPopulationJobResponseEnvelope()
                        .result(pkgJobApiService.queuePkgVersionPayloadLengthPopulationJob()));
    }

}
